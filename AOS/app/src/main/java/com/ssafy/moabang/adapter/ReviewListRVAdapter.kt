package com.ssafy.moabang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.moabang.R
import com.ssafy.moabang.data.model.dto.Review
import com.ssafy.moabang.databinding.ListReviewItemBinding

class ReviewListRVAdapter: RecyclerView.Adapter<ReviewListRVAdapter.ViewHolder>() {
    var data: List<Review> = emptyList()
    lateinit var binding: ListReviewItemBinding

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: Review){

            val userInfo = itemView.findViewById<TextView>(R.id.tv_reviewL_info)
            var txt = item.player.toString() + "인, "
            if(item.isSuccess == 1) txt.plus("탈출 성공")
            else txt.plus("탈출 실패")
            userInfo.text = txt

            val active = itemView.findViewById<TextView>(R.id.tv_reviewL_active)
            var act = "적음"
            if(item.active == 1) act = "보통"
            else if(item.active == 2) act = "많음"
            active.text = act

            itemView.findViewById<TextView>(R.id.tv_reviewL_name).text = item.userName
            itemView.findViewById<TextView>(R.id.tv_reviewL_date).text = item.regDate
            itemView.findViewById<RatingBar>(R.id.ratingBar_reviewL).rating = item.rating.toFloat()
            itemView.findViewById<TextView>(R.id.tv_reviewL_rating).text = item.rating.toString()
            itemView.findViewById<TextView>(R.id.tv_reviewL_diff).text = item.diff.toString()
            itemView.findViewById<TextView>(R.id.tv_reviewL_time).text = item.timeLeft
            itemView.findViewById<TextView>(R.id.tv_reviewL_hint).text = item.hint.toString() + "개"
            itemView.findViewById<TextView>(R.id.tv_reviewL_player).text = item.reqPlayer.toString() + "명"
            itemView.findViewById<TextView>(R.id.tv_reviewL_desc).text = item.desc
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_review_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(data[position])
        val revise = holder.itemView.findViewById<TextView>(R.id.tv_reviewL_revise)
        val delete = holder.itemView.findViewById<TextView>(R.id.tv_reviewL_delete)

        revise.setOnClickListener {
            // TODO
        }
        delete.setOnClickListener {
            // TODO
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}