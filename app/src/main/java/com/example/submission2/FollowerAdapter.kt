package com.example.submission2

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission2.databinding.ItemRowUserBinding

class FollowerAdapter(private var listFollower: ArrayList<User>) :
    RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    inner class FollowerViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("SetTextI18n")
            fun bind(userItems: User){
                with(binding){
                    Glide.with(itemView.context)
                        .load(userItems.avatar)
                        .apply(RequestOptions().override(100, 100))
                        .into(image)

                    tvRepository.text = userItems.repository
                    tvUsername.text = userItems.username
                    tvLocation.text = userItems.location

                    itemView.setOnClickListener {
                        onItemClickCallback?.onItemClicked(userItems)
                        Toast.makeText(
                            itemView.context,
                            "@${userItems.username}",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(itemView.context, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_USER, userItems)
                        itemView.context.startActivity(intent)
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.bind(listFollower[position])
    }

    override fun getItemCount(): Int = listFollower.size
}