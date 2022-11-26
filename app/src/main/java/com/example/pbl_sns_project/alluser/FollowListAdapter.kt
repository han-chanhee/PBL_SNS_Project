package com.example.snsproject.alluser


import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pbl_sns_project.alluser.followlist
import com.example.pbl_sns_project.databinding.ItemArticleBinding
import com.example.pbl_sns_project.databinding.ItemFollowListBinding

import java.util.Date

class FollowListAdapter(val onItemClicked: (followlist)-> Unit): ListAdapter<followlist, FollowListAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemFollowListBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(followlist: followlist){
            binding.root.setOnClickListener {
                onItemClicked(followlist)
            }
            binding.folllowlistname.text = followlist.followid

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemFollowListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])


    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<followlist>() {
            override fun areItemsTheSame(oldItem: followlist, newItem: followlist): Boolean {
                return oldItem.key == newItem.key
            }

            override fun areContentsTheSame(oldItem: followlist, newItem: followlist): Boolean {
                return oldItem == newItem
            }

        }

    }
}