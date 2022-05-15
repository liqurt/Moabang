package com.ssafy.moabang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.moabang.data.model.dto.Community
import com.ssafy.moabang.databinding.ListLatest5CommunityBinding

class Latest3CommunityRVAdapter(var latest3CommunityList: List<Community>) :
    RecyclerView.Adapter<Latest3CommunityRVAdapter.Latest3CommunityViewHolder>() {

    lateinit var itemClickListener: ItemClickListener

    inner class Latest3CommunityViewHolder(private val binding: ListLatest5CommunityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                if(this@Latest3CommunityRVAdapter::itemClickListener.isInitialized){
                    itemClickListener.onClick(latest3CommunityList[adapterPosition])
                }
            }
        }

        fun bindInfo(community: Community) {
            binding.tvHomeFL5cHeader.text = community.header
            binding.tvHomeFL5cContent.text = community.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Latest3CommunityViewHolder {
        val binding = ListLatest5CommunityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Latest3CommunityViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: Latest3CommunityRVAdapter.Latest3CommunityViewHolder,
        position: Int
    ) {
        holder.bindInfo(latest3CommunityList[position])
    }

    override fun getItemCount(): Int = latest3CommunityList.size

    interface ItemClickListener {
        fun onClick(community: Community)
    }
}