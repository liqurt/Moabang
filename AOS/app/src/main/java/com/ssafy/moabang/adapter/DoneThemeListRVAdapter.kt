package com.ssafy.moabang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.moabang.R
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.data.model.repository.Repository
import com.ssafy.moabang.data.model.response.DoneThemeResponse
import com.ssafy.moabang.data.model.viewmodel.ThemeViewModel
import com.ssafy.moabang.databinding.ListThemeItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DoneThemeListRVAdapter: RecyclerView.Adapter<DoneThemeListRVAdapter.ViewHolder>() {
    var data: List<DoneThemeResponse> = emptyList()
    lateinit var itemClickListener: ItemClickListener

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                if(this@DoneThemeListRVAdapter::itemClickListener.isInitialized){
                    itemClickListener.onClick(data[adapterPosition])
                }
            }
        }
        fun bind(item: DoneThemeResponse){
            val themeImg = itemView.findViewById<ImageView>(R.id.iv_themeL_img)
            val tvThemeName = itemView.findViewById<TextView>(R.id.tv_themeL_theme_name)
            val tvCafeName = itemView.findViewById<TextView>(R.id.tv_themeL_cafe_name)
            val tvGenre = itemView.findViewById<TextView>(R.id.tv_themeL_genre)
            val tvTime = itemView.findViewById<TextView>(R.id.tv_themeL_time)
            val tvRating = itemView.findViewById<TextView>(R.id.tv_themeL_rating)
            val tvDiff = itemView.findViewById<TextView>(R.id.tv_themeL_diff)
            val tvType = itemView.findViewById<TextView>(R.id.tv_themeL_type)
            val tvActive = itemView.findViewById<TextView>(R.id.tv_themeL_active)
            val tvPlayer = itemView.findViewById<TextView>(R.id.tv_themeL_player)
            itemView.findViewById<ImageView>(R.id.iv_themeL_like).visibility = View.GONE

            Glide.with(themeImg).load(item.img).into(themeImg)
            tvThemeName.text = item.tname
            tvCafeName.text = item.cname
            tvGenre.text = item.genre
            tvTime.text = item.time
            tvRating.text = item.grade.toString()
            tvDiff.text = item.difficulty.toString()
            tvType.text = item.type
            tvActive.text = if(item.activity == "") "-" else item.activity
            tvPlayer.text = item.rplayer + "명"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_theme_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    interface ItemClickListener {
        fun onClick(item: DoneThemeResponse)
    }
}