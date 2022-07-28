package com.jamshidbek.marketplaceinc.utils.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.DocumentSnapshot
import com.jamshidbek.marketplaceinc.R
import com.jamshidbek.marketplaceinc.databinding.SaleItemLayoutBinding
import com.jamshidbek.marketplaceinc.utils.models.ItemModel

class OnSaleRecyclerAdapter(
    private val productList: ArrayList<ItemModel>,
    val context: Context
) : RecyclerView.Adapter<OnSaleRecyclerAdapter.OnSaleRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnSaleRecyclerViewHolder =
        OnSaleRecyclerViewHolder(SaleItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: OnSaleRecyclerViewHolder, position: Int) {
        holder.binding.txtItemName.text = productList[position].itemName
        holder.binding.txtSellerName.text = productList[position].itemSeller

        if (productList[position].itemCurrency == "Dollars"){
            holder.binding.txtItemPrice.text = "$" + productList[position].itemPrice
        } else {
            holder.binding.txtItemPrice.text = productList[position].itemPrice + " " + productList[position].itemCurrency
        }

        Glide.with(context).load(productList[position].itemImgUrl)
            .placeholder(R.drawable.ic_add_image)
            .error(R.drawable.ic_add_image)
            .into(holder.binding.imgItemImage)
    }

    override fun getItemCount() = productList.size

    inner class OnSaleRecyclerViewHolder(val binding: SaleItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}