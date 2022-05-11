package com.ssafy.moabang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.moabang.R
import com.ssafy.moabang.config.GlobalApplication
import com.ssafy.moabang.data.model.dto.Theme
import com.ssafy.moabang.data.model.dto.ThemeForCompare
import com.ssafy.moabang.data.model.repository.Repository
import com.ssafy.moabang.data.model.viewmodel.ThemeViewModel
import com.ssafy.moabang.databinding.ListThemeItemBinding
import com.ssafy.moabang.src.util.CompareList
import com.ssafy.moabang.src.util.LocationUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompareThemeListRVAdapter: RecyclerView.Adapter<CompareThemeListRVAdapter.ViewHolder>() {
    var data: MutableList<ThemeForCompare> = mutableListOf()
    lateinit var binding: ListThemeItemBinding
    lateinit var itemClickListener: CompareThemeListRVAdapter.ItemClickListener

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                if(this@CompareThemeListRVAdapter::itemClickListener.isInitialized){
                    itemClickListener.onClick(data[adapterPosition])
                }
            }
        }
        fun bind(item: ThemeForCompare){
            val img = itemView.findViewById<ImageView>(R.id.iv_tCompare_img)
            Glide.with(img).load(item.img).centerCrop().into(img)

            itemView.findViewById<TextView>(R.id.tv_tCompare_theme_name).text = item.tname
            itemView.findViewById<TextView>(R.id.tv_tCompare_cafe_name).text = item.cname

            val current = LocationUtil().getCurrentLocation(itemView.context)
            if(current != null){
                val distance = LocationUtil().getDistanceLatLngInKm(current!!.latitude, current!!.longitude, item.lat!!.toDouble(), item.lon!!.toDouble())
                itemView.findViewById<TextView>(R.id.tv_tCompare_cafe_distance).text = String.format("%.1f", distance) + "km"
            } else {
                itemView.findViewById<TextView>(R.id.tv_tCompare_cafe_distance).text = ""
            }

            itemView.findViewById<TextView>(R.id.tv_tCompare_genre).text = item.genre
            itemView.findViewById<TextView>(R.id.tv_tCompare_time).text = item.time
            itemView.findViewById<TextView>(R.id.tv_tCompare_rating).text = item.grade.toString()
            itemView.findViewById<TextView>(R.id.tv_tCompare_diff).text = item.difficulty.toString()
            itemView.findViewById<TextView>(R.id.tv_tCompare_player).text = item.rplayer
            itemView.findViewById<TextView>(R.id.tv_tCompare_type).text = item.type
            itemView.findViewById<TextView>(R.id.tv_tCompare_active).text = item.activity
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_compare_theme_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])

        holder.itemView.findViewById<ImageView>(R.id.iv_tCompare_delete).setOnClickListener {
            data.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, data.size)
        }
    }

    override fun getItemCount(): Int = data.size

    interface ItemClickListener {
        fun onClick(item: ThemeForCompare)
    }
}