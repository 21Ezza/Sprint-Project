package com.example.sprint_project.data.model

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.sprint_project.UserListDetail
import com.example.sprint_project.databinding.UserListViewBinding

class AdapterUserList(private val context: Context): RecyclerView.Adapter<AdapterUserList.ViewHolder>() {

    private var itemListener: ((String, String, String) -> Unit)? = null
    private  val data: MutableList<User> = mutableListOf()
    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): ViewHolder{
        return ViewHolder(
            UserListViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.setData(data[position])
        holder.itemView.setOnClickListener{
            val intent = Intent (it.context, UserListDetail::class.java)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = data.size

    fun submitList(list: List<User>) {
        val initSize = itemCount
        data.clear()
        notifyItemRangeRemoved(0, initSize)
        data.addAll(list)
        notifyItemRangeInserted(0, data.size)
    }

    inner class ViewHolder(private val binding: UserListViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(item: User){
            with(binding) {
                tvUserName.text = "${item.firstName} ${item.lastName}"
                Glide
                    .with(root.context)
                    .load(item.avatar)
                    .into(ivUserList)
            }
        }
    }

    fun setOnItemClicker(listener: ((String, String, String) -> Unit)?){
        this.itemListener = listener
    }

}