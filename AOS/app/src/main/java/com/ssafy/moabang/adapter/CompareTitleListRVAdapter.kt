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
import com.ssafy.moabang.src.theme.ThemeCompareActivity
import com.ssafy.moabang.src.util.CompareList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompareTitleListRVAdapter: RecyclerView.Adapter<CompareTitleListRVAdapter.ViewHolder>() {
    var data: MutableList<ThemeForCompare> = mutableListOf()
    lateinit var binding: ListThemeItemBinding

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(item: ThemeForCompare){
            itemView.findViewById<TextView>(R.id.tv_ctItem_title).text = item.tname
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_compare_title_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])

        holder.itemView.findViewById<TextView>(R.id.tv_ctItem_add).setOnClickListener {
            CompareList.addTheme(data[position])
        }

        holder.itemView.findViewById<TextView>(R.id.tv_ctItem_delete).setOnClickListener {
            data.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, data.size)
        }
    }

    override fun getItemCount(): Int = data.size
}