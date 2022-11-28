package com.example.pbl_sns_project.alluser


import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.example.pbl_sns_project.databinding.ItemFollowListBinding



class FollowListAdapter(val onItemClicked: (Followlist)-> Unit): ListAdapter<Followlist, FollowListAdapter.ViewHolder>(
    diffUtil
) {
    inner class ViewHolder(private val binding: ItemFollowListBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(followlist: Followlist){
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
        val diffUtil = object : DiffUtil.ItemCallback<Followlist>() {
            override fun areItemsTheSame(oldItem: Followlist, newItem: Followlist): Boolean {
                return oldItem.key == newItem.key
            }

            override fun areContentsTheSame(oldItem: Followlist, newItem: Followlist): Boolean {
                return oldItem == newItem
            }

        }

    }
}