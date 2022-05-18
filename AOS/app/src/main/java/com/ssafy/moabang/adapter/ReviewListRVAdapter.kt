package com.ssafy.moabang.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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
import com.ssafy.moabang.src.util.ReportDialog

class ReviewListRVAdapter : RecyclerView.Adapter<ReviewListRVAdapter.ViewHolder>() {
    var data: MutableList<ReviewResponse> = mutableListOf()
    lateinit var binding: ListReviewItemBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ReviewResponse) {// 신고 3회 넘어가면 안 보이게

            itemView.findViewById<FrameLayout>(R.id.blindReview).visibility = View.GONE
            val revise = itemView.findViewById<TextView>(R.id.tv_reviewL_revise)
            val delete = itemView.findViewById<TextView>(R.id.tv_reviewL_delete)
            val report = itemView.findViewById<TextView>(R.id.tv_reviewL_report)

            if (item.userInfo.uid != sp.getInt("uid")) {
                revise.visibility = View.GONE
                delete.visibility = View.GONE
                report.visibility = View.VISIBLE
            } else {
                revise.visibility = View.VISIBLE
                delete.visibility = View.VISIBLE
                report.visibility = View.GONE
            }

            val userInfo = itemView.findViewById<TextView>(R.id.tv_reviewL_info)
            var txt = item.player.toString() + "인, "
            if (item.isSuccess == 1) txt += "탈출 성공, "
            else txt += "탈출 실패, "
            txt += item.playDate
            userInfo.text = txt

            itemView.findViewById<TextView>(R.id.tv_reviewL_name).text = item.userInfo.nickname
            itemView.findViewById<TextView>(R.id.tv_reviewL_date).text = item.regDate

            if(item.reportCnt<=3){
                itemView.findViewById<TextView>(R.id.tv_reviewL_active).text = item.active
                itemView.findViewById<SimpleRatingBar>(R.id.ratingBar_reviewL).rating = item.rating / 2
                itemView.findViewById<TextView>(R.id.tv_reviewL_rating).text = item.rating.toString()
                itemView.findViewById<TextView>(R.id.tv_reviewL_diff).text = item.chaegamDif.toString()
                itemView.findViewById<TextView>(R.id.tv_reviewL_time).text = item.clearTime.toString()
                itemView.findViewById<TextView>(R.id.tv_reviewL_hint).text = item.hint.toString() + "개"
                itemView.findViewById<TextView>(R.id.tv_reviewL_player).text =
                    item.recPlayer.toString() + "명"
                itemView.findViewById<TextView>(R.id.tv_reviewL_desc).text = item.content
            }else{
                itemView.findViewById<FrameLayout>(R.id.blindReview).visibility = View.VISIBLE
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_review_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])

        holder.itemView.findViewById<TextView>(R.id.tv_reviewL_revise).setOnClickListener {
            val intent = Intent(holder.itemView.context, ReviewActivity::class.java)
                .putExtra("type", "수정")
                .putExtra("review", data[position].rid)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }

        holder.itemView.findViewById<TextView>(R.id.tv_reviewL_delete).setOnClickListener {
            CustomDialog(holder.itemView.context)
                .setContent("리뷰를 삭제하시겠습니까?")
                .setPositiveButtonText("삭제")
                .setOnPositiveClickListener {
                    ReviewViewModel().reviewDelete(data[position].rid)
                    data.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, data.size)
                }.build().show()
        }

        // TODO: 신고 버튼 눌렀을때 처리
        // 리뷰 신고
        holder.itemView.findViewById<TextView>(R.id.tv_reviewL_report).setOnClickListener {
            // 이미 신고한 리뷰일때
//            CustomDialog(holder.itemView.context)
//                .setContent("이미 신고한 리뷰입니다.")
//                .setNegativeButtonText("")
//                .build().show()

            // 처음 신고하는 리뷰일때
            CustomDialog(holder.itemView.context)
                .setContent("해당 리뷰를 신고하시겠습니까?")
                .setNegativeButtonText("취소")
                .setPositiveButtonText("신고하기")
                .setOnPositiveClickListener {
                    val dialog = ReportDialog(
                        holder.itemView.context,
                        data[holder.adapterPosition].rid,
                        0,
                        data[holder.adapterPosition].content
                    )
                    dialog.show()
                }.build().show()
        }
    }

    fun sortList(list: List<ReviewResponse>) {
        data = list as MutableList<ReviewResponse>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

}