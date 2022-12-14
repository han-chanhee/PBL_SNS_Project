package com.example.pbl_sns_project.home


import android.annotation.SuppressLint
import android.content.Intent

import android.os.Bundle
import android.view.View

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pbl_sns_project.DBkey.Companion.DB_ARTICLES
import com.example.pbl_sns_project.DBkey.Companion.DB_USERS
import com.example.pbl_sns_project.DBkey.Companion.RELATION

import com.example.pbl_sns_project.R
import com.example.pbl_sns_project.alluser.Followlist
import com.example.pbl_sns_project.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase



class HomeFragment : Fragment(R.layout.fragment_home){
    private lateinit var auth : FirebaseAuth
    private  lateinit var articleDB: DatabaseReference
    private  lateinit var userDB: DatabaseReference
    private lateinit var articleAdapter: ArticleAdapter
    private val articleList = mutableListOf<ArticleModel>()
    private val listener = object : ChildEventListener{
        @SuppressLint("SuspiciousIndentation")
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
          val articleModel =snapshot.getValue(ArticleModel::class.java)

            articleModel ?: return
            articleList.add(articleModel)
            articleAdapter.submitList(articleList)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onChildRemoved(snapshot: DataSnapshot) {

        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onCancelled(error: DatabaseError) {

        }


    }

    private var binding: FragmentHomeBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onViewCreated(view, savedInstanceState)
        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        articleAdapter = ArticleAdapter(onItemClickedButton = { articleModel ->
            if (auth.currentUser != null) {
                if (auth.currentUser!!.email != articleModel.userId) {


                    Snackbar.make(view, "????????? ??????${articleModel.userId}", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(view, "?????? ?????????:${auth.currentUser!!.email}", Snackbar.LENGTH_LONG).show()
                }

            }
        })

        binding = fragmentHomeBinding
        articleList.clear()
        articleDB = Firebase.database.reference.child(DB_USERS)
        articleDB = Firebase.database.reference.child(DB_ARTICLES)





        fragmentHomeBinding.articleRecyclerView.layoutManager = LinearLayoutManager(context)
        fragmentHomeBinding.articleRecyclerView.adapter = articleAdapter

        fragmentHomeBinding.addFloatingButton.setOnClickListener{

            context?.let {

                if(auth.currentUser != null){
                    val intent = Intent(it, AddArticleActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Snackbar.make(view,"????????? ??? ?????? ??????",Snackbar.LENGTH_LONG).show()
                }

            }
        }


        articleDB.addChildEventListener(listener)


    }


    override fun onResume() {
        super.onResume()
        articleAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        articleDB.removeEventListener(listener)
    }

}