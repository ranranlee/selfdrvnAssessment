package com.example.selfdrvnassessment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.selfdrvnassessment.R
import com.example.selfdrvnassessment.model.AvengersDetailsModel
import com.example.selfdrvnassessment.roomDatabase.database.entity.AvengersListEntity
import kotlinx.android.synthetic.main.row_avenger_list.view.*
import java.util.*

class AvengersAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemList: ArrayList<AvengersListEntity> = ArrayList()
    private lateinit var mClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_avenger_list, parent, false)
        return AvengersListVH(view, mClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val avengersListVH = holder as AvengersListVH
        avengersListVH.setData(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.mClickListener = itemClickListener
    }
    interface ItemClickListener {

        fun onItemClick(model: AvengersListEntity)
    }

    fun setData(itemList: ArrayList<AvengersListEntity>){
        this.itemList = itemList
        notifyDataSetChanged()
    }

    class AvengersListVH(view: View, var listener: ItemClickListener) : RecyclerView.ViewHolder(view) {
        fun setData(item: AvengersListEntity){
            Glide.with(itemView.context).load(item.imagePath).into(itemView.ivImage)

            itemView.tvName.text = item.name
            Log.i("check","check data::${item.name}")
            when (item.rating) {
                3 -> {
                    itemView.tvRating.text = itemView.context.getString(R.string.rating_3)
                }
                2 -> {
                    itemView.tvRating.text = itemView.context.getString(R.string.rating_2)
                }
                1 -> {
                    itemView.tvRating.text = itemView.context.getString(R.string.rating_1)
                }
            }

            itemView.setOnClickListener {
                listener.onItemClick(item)
            }

        }



    }
}