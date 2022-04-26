package com.ssafy.moabang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.moabang.R
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.databinding.ListThemeItemBinding

class ThemeListRVAdapter: RecyclerView.Adapter<ThemeListRVAdapter.ViewHolder>() {
    var data: List<Theme> = emptyList()
    lateinit var binding: ListThemeItemBinding
    lateinit var itemClickListener: AdapterView.OnItemClickListener

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: Theme){
            val themeImg = itemView.findViewById<ImageView>(R.id.iv_themeL_img)
            val tvThemeName = itemView.findViewById<TextView>(R.id.tv_themeL_theme_name)
            val tvCafeName = itemView.findViewById<TextView>(R.id.tv_themeL_cafe_name)
            val tvGenre = itemView.findViewById<TextView>(R.id.tv_themeL_genre)
            val tvTime = itemView.findViewById<TextView>(R.id.tv_themeL_time)
            val tvRating = itemView.findViewById<TextView>(R.id.tv_themeL_rating)
            val tvDiff = itemView.findViewById<TextView>(R.id.tv_themeL_diff)
            val tvPlayer = itemView.findViewById<TextView>(R.id.tv_themeL_player)
            val like = itemView.findViewById<ImageView>(R.id.iv_themeL_like)

            like.setOnClickListener {
                if(item.like){
                    item.like = false
                    like.setImageResource(R.drawable.icon_like_before)
                } else {
                    item.like = true
                    like.setImageResource(R.drawable.icon_like_after)
                }
            }

            Glide.with(themeImg).load(item.img).into(themeImg)
            tvThemeName.text = item.name
            tvCafeName.text = item.cafeName
            tvGenre.text = item.genre
            tvTime.text = item.time.toString() + "분"
            tvRating.text = item.rating.toString()
            tvDiff.text = item.difficulty.toString()
            tvPlayer.text = item.player + "명"
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
        fun onClick(position: Int)
    }
}