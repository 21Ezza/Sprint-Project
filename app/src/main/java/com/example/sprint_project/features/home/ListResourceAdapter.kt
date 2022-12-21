package com.example.sprint_project.features.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sprint_project.data.model.ListPageResource
import com.example.sprint_project.data.model.ListResource
import com.example.sprint_project.databinding.ItemResourceBinding

class ListResourceAdapter : RecyclerView.Adapter<ListResourceAdapter.ViewHolder>() {

    private val data = mutableListOf<ListResource>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemResourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun submitList(list: List<ListResource>) {
        val initSize = itemCount
        data.clear()
        notifyItemRangeRemoved(0, initSize)
        data.addAll(list)
        notifyItemRangeInserted(0, data.size)
    }

    inner class ViewHolder(private val binding: ItemResourceBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(item: ListResource) {
            with(binding) {
                tvText.text = item.name
            }
        }
    }
}