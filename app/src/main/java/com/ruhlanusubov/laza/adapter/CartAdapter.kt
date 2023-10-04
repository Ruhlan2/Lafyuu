package com.ruhlanusubov.laza.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ruhlanusubov.laza.databinding.ItemcartBinding
import com.ruhlanusubov.laza.model.cart.Cart
import com.ruhlanusubov.laza.model.cart.CartProduct

class CartAdapter: RecyclerView.Adapter<CartAdapter.CartHolder>() {

    private val cartList=ArrayList<CartProduct>()

    inner class CartHolder(val binding:ItemcartBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val layout=ItemcartBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartHolder(layout)
    }

    override fun getItemCount(): Int {
       return cartList.size
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        val item=cartList[position]
        with(holder.binding){
            productName.text=item.title
            productPrice.text="$${item.price}"
            quantity.text="${item.quantity}"
        }
    }
    fun updateList(list: List<CartProduct>){
        cartList.clear()
        cartList.addAll(list)
        notifyDataSetChanged()
    }
}