package com.ruhlanusubov.laza.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ruhlanusubov.laza.databinding.ItemcartBinding

class CartAdapter: RecyclerView.Adapter<CartAdapter.CartHolder>() {



    inner class CartHolder(val binding:ItemcartBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        TODO("Not yet implemented")
    }
}