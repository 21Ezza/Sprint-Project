package com.example.sprint_project.data.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.sprint_project.databinding.ItemViewBinding

class AdapterItem(private val data: MutableList<ItemModel> = mutableListOf()): RecyclerView.Adapter<AdapterItem.ViewHolder>() {

    private var itemClickListener: ((String, String, String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.validateBind(data[position], itemClickListener)
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding: ItemViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun validateBind(item: ItemModel, listener: ((String, String, String) -> Unit)?){
            binding.root.setOnClickListener {
                listener?.invoke(item.title, item.accountDetail, item.transactionAmount)
            }
            when(item.type){
                "Keluar" -> {
                    binding.tvTitle.text = item.title
                    binding.tvAccountInfo.text = item.accountDetail
                    binding.tvTransactionAmount.text = item.transactionAmount
                    binding.cardDecrease.isVisible = true
                    binding.cardIncrease.isVisible = false
                }
                "Masuk" -> {
                    binding.tvTitle1.text = item.title
                    binding.tvAccountInfo1.text = item.accountDetail
                    binding.tvTransactionAmount1.text = item.transactionAmount
                    binding.cardDecrease.isVisible = false
                    binding.cardIncrease.isVisible = true
                }
            }
        }
    }
    fun setOnClickItemListener(listener: (title: String, accountDetail: String, transactionAmount: String) -> Unit){
        this.itemClickListener = listener
    }
}