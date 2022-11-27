package com.example.snsproject.mypage


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pbl_sns_project.R
import com.example.snsproject.home.ArticleModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_mypage.view.*





class MyPageFragment: Fragment() {
    var fragmentView : View? =null
    var firestore : FirebaseFirestore? = null
    var uid : String? = null
    var auth : FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = LayoutInflater.from(activity).inflate(R.layout.fragment_mypage, container, false)

        uid = arguments?.getString("destinationUid")
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        fragmentView?.account_recyclerView?.adapter = MyPageFragmentRecyclerViewAdapter()
        fragmentView?.account_recyclerView?.layoutManager = GridLayoutManager(requireActivity(),3)

        return fragmentView
    }

    inner class MyPageFragmentRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var ArticleModels: ArrayList<ArticleModel> = arrayListOf()

        init {
            firestore?.collection("images")?.whereEqualTo("uid", uid)
                ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    if (querySnapshot == null) return@addSnapshotListener

                    // 데이터 받아오기
                    for (snapshot in querySnapshot.documents) {
                        ArticleModels.add(snapshot.toObject(ArticleModel::class.java)!!)
                    }
                    fragmentView?.account_post_count?.text = ArticleModels.size.toString()
                    notifyDataSetChanged()

                }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var width = resources.displayMetrics.widthPixels / 3

            var imageView = ImageView(parent.context)
            imageView.layoutParams = LinearLayoutCompat.LayoutParams(width, width)
            return CustomViewHolder(imageView)
        }

        inner class CustomViewHolder(var imageView: ImageView) :
            RecyclerView.ViewHolder(imageView) {
        }

        override fun getItemCount(): Int {
            return ArticleModels.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var imageView = (holder as CustomViewHolder).imageView
            Glide.with(holder.itemView.context).load(ArticleModels[position].imageUrl).apply(
                RequestOptions().centerCrop()
            ).into(imageView)

        }
    }

}