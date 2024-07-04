package com.umesh.greenshop.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.umesh.greenshop.Activity.DetailActivity
import com.umesh.greenshop.Model.ItemsModel
import com.umesh.greenshop.databinding.ViewholderRecommendBinding

class RecommendationAdapter(val items: MutableList<ItemsModel>) :
    RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {
    private var context: Context? = null

    class ViewHolder(val binding: ViewholderRecommendBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendationAdapter.ViewHolder {
        context = parent.context
        val binding =
            ViewholderRecommendBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationAdapter.ViewHolder, position: Int) {
        holder.binding.titleText.text = items[position].title
        holder.binding.priceText.text = "$" + items[position].price.toString()
        holder.binding.ratingtext.text = items[position].rating.toString()

        Glide.with(holder.itemView.context).load(items[position].picUrl[0])
            .apply(RequestOptions.centerCropTransform()).into(holder.binding.productPic)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("object", items[position])
            holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int = items.size

}