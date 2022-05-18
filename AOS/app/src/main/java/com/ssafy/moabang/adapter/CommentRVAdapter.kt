package com.ssafy.moabang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kakao.sdk.user.UserApiClient
import com.ssafy.moabang.R
import com.ssafy.moabang.config.GlobalApplication
import com.ssafy.moabang.data.model.dto.Comment
import com.ssafy.moabang.data.model.dto.CommentUpdateRequest
import com.ssafy.moabang.data.model.repository.CommunityRepository
import com.ssafy.moabang.databinding.ListCommentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentRVAdapter : RecyclerView.Adapter<CommentRVAdapter.CommentRVAdapterViewHolder>() {

    var communityRepository = CommunityRepository()
    lateinit var data: MutableList<Comment>

    inner class CommentRVAdapterViewHolder(private val binding: ListCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindInfo(comment: Comment) {
            Glide.with(binding.root.context)
                .load(comment.userProfile)
                .placeholder(R.drawable.door)
                .centerCrop()
                .into(binding.civCommentItem)
            binding.tvCommentItemAuthor.text = comment.userName
            binding.tvCommentDate.text = comment.regDate
            binding.tvCommentContent.text = comment.content

            if(comment.uid == GlobalApplication.sp.getInt("uid")){
                binding.btCommentRemove.visibility = ViewGroup.VISIBLE
                binding.btCommentEdit.visibility = ViewGroup.VISIBLE
                binding.btCommentReport.visibility = View.GONE

                binding.btCommentRemove.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch { communityRepository.deleteComment(comment.coid) }
                    data.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    notifyItemRangeChanged(adapterPosition, data.size)
                }

                binding.btCommentEdit.setOnClickListener {
                    binding.tvCommentContent.visibility = ViewGroup.GONE
                    binding.etCommentContent.visibility = ViewGroup.VISIBLE
                    binding.btCommentEdit.visibility = ViewGroup.GONE
                    binding.btCommentEditDone.visibility = ViewGroup.VISIBLE
                }

                binding.btCommentEditDone.setOnClickListener {
                    binding.tvCommentContent.visibility = ViewGroup.VISIBLE
                    binding.etCommentContent.visibility = ViewGroup.GONE
                    binding.btCommentEdit.visibility = ViewGroup.VISIBLE
                    binding.btCommentEditDone.visibility = ViewGroup.GONE


                    binding.tvCommentContent.text = binding.etCommentContentInput.text.toString()
                    CoroutineScope(Dispatchers.IO).launch {
                        val temp = CommentUpdateRequest( comment.coid,binding.etCommentContentInput.text.toString())
                        communityRepository.updateComment(temp)
                    }
                }
            }else{
                binding.btCommentRemove.visibility = ViewGroup.GONE
                binding.btCommentEdit.visibility = ViewGroup.GONE
                binding.btCommentReport.visibility = View.VISIBLE
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentRVAdapter.CommentRVAdapterViewHolder {
        val binding = ListCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentRVAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CommentRVAdapter.CommentRVAdapterViewHolder,
        position: Int
    ) {
        holder.bindInfo(data[position])
    }

    override fun getItemCount(): Int = data.size
}