package com.ruhlanusubov.laza.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ruhlanusubov.laza.databinding.ItemproductBinding
import com.ruhlanusubov.laza.model.product.Product
import com.ruhlanusubov.laza.presentation.ui.home.HomeFragmentDirections

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.ProductHolder>() {
    private val productList=ArrayList<Product>()
    var onClickItemListener: (Product)->Unit={}
    inner class ProductHolder(val binding:ItemproductBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val layout=ItemproductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductHolder(layout)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val item=productList[position]
        holder.binding.productname.text=item.title
        holder.binding.productprice.text="$ ${item.price}"
        val num=item.discountPercentage
        val decrease=(100.0-num!!).toInt()
        val firstPrice=item.price?.let { it*100/decrease }
        holder.binding.discounted.text="$ ${firstPrice}"
        holder.binding.discounted.paintFlags= Paint.STRIKE_THRU_TEXT_FLAG
        holder.binding.percentage.text="% ${item.discountPercentage}"
        Glide.with(holder.itemView.context).load(item.images?.let { it[0] }).into(holder.binding.productimg)
/*
        holder.binding.card.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item))
        }

 */
       holder.binding.card.setOnClickListener {
            onClickItemListener(item)
       }
    }
    fun updateList(list: List<Product>){
        productList.clear()
        productList.addAll(list)
        notifyDataSetChanged()
    }
}