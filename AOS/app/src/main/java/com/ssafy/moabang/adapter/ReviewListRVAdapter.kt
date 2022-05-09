package com.ssafy.moabang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iarcuschin.simpleratingbar.SimpleRatingBar
import com.ssafy.moabang.R
import com.ssafy.moabang.data.model.dto.Review
import com.ssafy.moabang.data.model.response.ReviewResponse
import com.ssafy.moabang.databinding.ListReviewItemBinding

class ReviewListRVAdapter: RecyclerView.Adapter<ReviewListRVAdapter.ViewHolder>() {
    var data: List<ReviewResponse> = emptyList()
    lateinit var binding: ListReviewItemBinding

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: ReviewResponse){

            val revise = itemView.findViewById<TextView>(R.id.tv_reviewL_revise)
            val delete = itemView.findViewById<TextView>(R.id.tv_reviewL_delete)

            // TODO: 리뷰 작성자가 본인일때만 수정 삭제 버튼 보이게 함
            revise.setOnClickListener {
                // TODO
            }
            delete.setOnClickListener {
                // TODO
            }

            val userInfo = itemView.findViewById<TextView>(R.id.tv_reviewL_info)
            var txt = item.player.toString() + "인, "
            if(item.isSuccess == 1) txt += "탈출 성공, "
            else txt += "탈출 실패, "
            txt += item.playDate
            userInfo.text = txt

            itemView.findViewById<TextView>(R.id.tv_reviewL_active).text = item.active
            itemView.findViewById<TextView>(R.id.tv_reviewL_name).text = item.userInfo.nickname
            itemView.findViewById<TextView>(R.id.tv_reviewL_date).text = item.regDate
            itemView.findViewById<SimpleRatingBar>(R.id.ratingBar_reviewL).rating = item.rating/2
            itemView.findViewById<TextView>(R.id.tv_reviewL_rating).text = item.rating.toString()
            itemView.findViewById<TextView>(R.id.tv_reviewL_diff).text = item.chaegamDif.toString()
            itemView.findViewById<TextView>(R.id.tv_reviewL_time).text = item.clearTime.toString()
            itemView.findViewById<TextView>(R.id.tv_reviewL_hint).text = item.hint.toString() + "개"
            itemView.findViewById<TextView>(R.id.tv_reviewL_player).text = item.recPlayer.toString() + "명"
            itemView.findViewById<TextView>(R.id.tv_reviewL_desc).text = item.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_review_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(data[position])
            }

    override fun getItemCount(): Int {
        return data.size
    }

}