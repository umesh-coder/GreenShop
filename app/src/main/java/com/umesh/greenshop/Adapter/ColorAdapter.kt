package com.umesh.greenshop.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umesh.greenshop.R
import com.umesh.greenshop.databinding.ViewholderColorBinding

class ColorAdapter(val items: MutableList<String>) :
    RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var lastSelectionPosition = -1
    private lateinit var context: Context


    inner class ViewHolder(val binding: ViewholderColorBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorAdapter.ViewHolder {
        context = parent.context
        val binding = ViewholderColorBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ColorAdapter.ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(items[position]).into(holder.binding.pic)
        holder.binding.root.setOnClickListener {

            lastSelectionPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(lastSelectionPosition)
            notifyItemChanged(selectedPosition)
        }
        if (selectedPosition == position) {
            holder.binding.colorLayout.setBackgroundResource(R.drawable.white_bg_selected)
        } else {
            holder.binding.colorLayout.setBackgroundResource(R.drawable.white_bg)
        }

    }

    override fun getItemCount(): Int = items.size


}