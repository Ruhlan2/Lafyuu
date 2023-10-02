package com.ruhlanusubov.laza.adapter

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ruhlanusubov.laza.databinding.ItemmainBinding
import com.ruhlanusubov.laza.model.product.Product
import com.ruhlanusubov.laza.presentation.ui.home.HomeFragmentDirections

class MainProductAdapter : RecyclerView.Adapter<MainProductAdapter.MainHolder>() {
    private val homeList=ArrayList<Product>()
    var onClickItemListener:(Product)->Unit={}
    inner class MainHolder(val binding:ItemmainBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
       val layout=ItemmainBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MainHolder(layout)
    }

    override fun getItemCount(): Int {
        return homeList.size
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
       val item=homeList[position]
        holder.binding.productname.text=item.title
        holder.binding.productprice.text="$ ${item.price}"
        val num=item.discountPercentage
        val decrease=(100.0-num!!).toInt()
        val firstPrice=item.price?.let { it*100/decrease }
        holder.binding.discounted.text="$ ${firstPrice}"
        holder.binding.discounted.paintFlags= Paint.STRIKE_THRU_TEXT_FLAG
        holder.binding.percentage.text="% ${item.discountPercentage}"
        holder.binding.appCompatRatingBar.rating=item.rating!!.toFloat()
        Glide.with(holder.itemView.context).load(item.thumbnail).into(holder.binding.productimg)


        /*
        holder.binding.productCard.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item))
        }

         */
        holder.binding.productCard.setOnClickListener {
            onClickItemListener(item)
        }
    }
    fun updateList(list: List<Product>){
        homeList.clear()
        homeList.addAll(list)
        notifyDataSetChanged()
    }

}