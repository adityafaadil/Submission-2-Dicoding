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
import kotlin.collections.ArrayList


class UserAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    inner class UserViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(image)

                tvRepository.text = checkNull(user.repository)
                tvUsername.text = "@" + checkNull(user.username)
                tvLocation.text = checkNull(user.location)

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(user)
                    Toast.makeText(
                        itemView.context,
                        "@${user.username}",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USER, user)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    private fun checkNull(string: String?): String? {
        if (string == "null"){
            return "-"
        }
        return string
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

}