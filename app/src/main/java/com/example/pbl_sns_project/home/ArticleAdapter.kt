package com.example.snsproject.home


import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pbl_sns_project.databinding.ItemArticleBinding

import java.util.Date

class ArticleAdapter(val onItemClicked: (ArticleModel)-> Unit): ListAdapter<ArticleModel, ArticleAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemArticleBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(articleModel: ArticleModel){
            val format = SimpleDateFormat("MM월 dd일")
            val date = Date(articleModel.createdAt)
            binding.thumbnailImageView
            binding.dateTextView.text = format.format(date).toString()
            binding.titleTextView.text = articleModel.title
            binding.emailTextView.text = articleModel.userId
            binding.wTextView.text = articleModel.weather
            binding.cTextView.text = articleModel.content

            if (articleModel.imageUrl.isNotEmpty()) {
                Glide.with(binding.thumbnailImageView)
                    .load(articleModel.imageUrl)
                    .into(binding.thumbnailImageView)
            }
            binding.button2.setOnClickListener {
                onItemClicked(articleModel)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])


    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ArticleModel>() {
            override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem.createdAt == newItem.createdAt
            }

            override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem == newItem
            }

        }

    }
}