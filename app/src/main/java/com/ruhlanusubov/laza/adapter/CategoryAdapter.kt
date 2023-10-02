package com.ruhlanusubov.laza.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ruhlanusubov.laza.databinding.ItemcategoryBinding

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {
    private val name=ArrayList<String>()
    inner class CategoryHolder(val binding: ItemcategoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val layout=ItemcategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryHolder(layout)
    }

    override fun getItemCount(): Int {
      return name.size
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val item=name[position]
        holder.binding.category.text=item
    }
    fun updateList(list: List<String>){
        name.clear()
        name.addAll(list)
        notifyDataSetChanged()
    }
}