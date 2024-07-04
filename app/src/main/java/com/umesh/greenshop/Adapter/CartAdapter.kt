package com.umesh.greenshop.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.umesh.greenshop.Helper.ChangeNumberItemsListener
import com.umesh.greenshop.Helper.ManagmentCart
import com.umesh.greenshop.Model.ItemsModel
import com.umesh.greenshop.databinding.ViewholderCartBinding

class CartAdapter(
    private val listItemSelected: ArrayList<ItemsModel>,
    context: Context,
    var changeNumberItemsListener: ChangeNumberItemsListener? = null,

    ) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(val binding: ViewholderCartBinding) : RecyclerView.ViewHolder(binding.root)

    private val managmentCart = ManagmentCart(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val binding =
            ViewholderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        val item = listItemSelected[position]
        holder.binding.titleText.text = item.title
        holder.binding.feeEachItemText.text = "$" + item.price.toString()
        holder.binding.totalEachItemText.text =
            "$" + Math.round((item.price * item.numberInCart)).toString()
        holder.binding.numberText.text = item.numberInCart.toString()

        Glide.with(holder.itemView.context).load(item.picUrl[0])
            .apply(RequestOptions().transform(CenterCrop())).into(holder.binding.ppic)

        holder.binding.plusCartText.setOnClickListener {
            managmentCart.plusItem(listItemSelected, position, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener?.onChanged()
                }

            })

        }

        holder.binding.minusCartText.setOnClickListener {
            managmentCart.minusItem(listItemSelected, position, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener?.onChanged()
                }

            })

        }


    }

    override fun getItemCount(): Int = listItemSelected.size


}