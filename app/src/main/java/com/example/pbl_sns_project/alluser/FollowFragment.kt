package com.example.snsproject.alluser

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pbl_sns_project.DBkey.Companion.DB_USERS
import com.example.pbl_sns_project.DBkey.Companion.RELATION
import com.example.pbl_sns_project.R
import com.example.pbl_sns_project.alluser.followlist

import com.example.pbl_sns_project.databinding.FragmentFollowBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class FollowFragment: Fragment(R.layout.fragment_follow) {
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }
    private var binding: FragmentFollowBinding? = null
    private lateinit var followlistAdapter: FollowListAdapter
    private  val followidlist = mutableListOf<followlist>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentAlluserBinding = FragmentFollowBinding.bind(view)
        binding = fragmentAlluserBinding

        followlistAdapter = FollowListAdapter(onItemClicked = {})

        followidlist.clear()

        fragmentAlluserBinding.followRecyclerView.adapter = followlistAdapter
        fragmentAlluserBinding.followRecyclerView.layoutManager = LinearLayoutManager(context)

        val followDB = Firebase.database.reference.child(DB_USERS).child(auth.currentUser!!.uid).child(
            RELATION)
        followDB.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    val model = it.getValue(followlist::class.java)
                    model ?: return
                    followidlist.add(model)
                }
                followlistAdapter.submitList(followidlist)
                followlistAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }


    override fun onResume() {
        super.onResume()
        followlistAdapter.notifyDataSetChanged()
    }
}