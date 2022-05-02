package com.ssafy.moabang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.moabang.data.model.dto.Cafe
import com.ssafy.moabang.databinding.ListCafeItemBinding

class CafeListRVAdapter(private val cafeList: List<Cafe>) :
    RecyclerView.Adapter<CafeListRVAdapter.CafeViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CafeViewHolder {
        val binding = ListCafeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return CafeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CafeViewHolder, position: Int) {
        holder.bindInfo(cafeList[position])
    }

    override fun getItemCount(): Int = cafeList.size


    inner class CafeViewHolder(private val binding: ListCafeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindInfo(cafe : Cafe) {
            Glide.with(binding.root.context).load(cafe.img).centerCrop().into(binding.iv1)
            binding.tv1.text = cafe.cname
            binding.tv2.text = cafe.location
            binding.tv3.text = cafe.cphone
        }
    }

    interface CafeItemClickListener {
        fun onClick(cafe: Cafe, position: Int)
    }

    lateinit var listener: CafeItemClickListener
    fun setItemClickListener(listener: CafeItemClickListener) {
        this.listener = listener
    }

}