package com.ssafy.moabang.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.iarcuschin.simpleratingbar.SimpleRatingBar
import com.ssafy.moabang.R
import com.ssafy.moabang.config.GlobalApplication.Companion.sp
import com.ssafy.moabang.data.model.response.ReviewResponse
import com.ssafy.moabang.data.model.viewmodel.ReviewViewModel
import com.ssafy.moabang.databinding.ListReviewItemBinding
import com.ssafy.moabang.src.theme.ReviewActivity
import com.ssafy.moabang.src.util.CustomDialog

class ReviewListRVAdapter: RecyclerView.Adapter<ReviewListRVAdapter.ViewHolder>() {
    var data: MutableList<ReviewResponse> = mutableListOf()
    lateinit var binding: ListReviewItemBinding

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: ReviewResponse){

            val revise = itemView.findViewById<TextView>(R.id.tv_reviewL_revise)
            val delete = itemView.findViewById<TextView>(R.id.tv_reviewL_delete)

            if(item.userInfo.uid != sp.getInt("uid")){
                revise.visibility = View.GONE
                delete.visibility = View.GONE
            }else{
                revise.visibility = View.VISIBLE
                delete.visibility = View.VISIBLE
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

        holder.itemView.findViewById<TextView>(R.id.tv_reviewL_revise).setOnClickListener {
            val intent = Intent(holder.itemView.context, ReviewActivity::class.java)
                .putExtra("type", "수정")
                .putExtra("review", data[position])
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }

        holder.itemView.findViewById<TextView>(R.id.tv_reviewL_delete).setOnClickListener {
            CustomDialog(holder.itemView.context)
                .setContent("리뷰를 삭제하시겠습니까?")
                .setPositiveButtonText("삭제")
                .setOnPositiveClickListener{
                    ReviewViewModel().reviewDelete(data[position].rid)
                    data.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, data.size)
                }.build().show()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}