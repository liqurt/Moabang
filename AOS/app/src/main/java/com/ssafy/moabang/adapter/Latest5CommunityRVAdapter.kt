package com.ssafy.moabang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.moabang.data.model.dto.Community
import com.ssafy.moabang.databinding.ListLatest5CommunityBinding

class Latest5CommunityRVAdapter(var latest5CommunityList: List<Community>) :
    RecyclerView.Adapter<Latest5CommunityRVAdapter.Latest5CommunityViewHolder>() {

    inner class Latest5CommunityViewHolder(private val binding: ListLatest5CommunityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindInfo(community: Community) {
            binding.tvHomeFL5cHeader.text = community.header
            binding.tvHomeFL5cContent.text = community.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Latest5CommunityViewHolder {
        val binding = ListLatest5CommunityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Latest5CommunityViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: Latest5CommunityRVAdapter.Latest5CommunityViewHolder,
        position: Int
    ) {
        holder.bindInfo(latest5CommunityList[position])
    }

    override fun getItemCount(): Int = latest5CommunityList.size

}