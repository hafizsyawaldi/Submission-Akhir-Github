package com.example.submisionawal.adaptor

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submisionawal.api.ApiConfig
import com.example.submisionawal.data.DetailUserResponse
import com.example.submisionawal.data.ItemsItem
import com.example.submisionawal.databinding.ItemUserRowBinding
import com.example.submisionawal.view.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteAdapter(private val listUsers: ArrayList<ItemsItem>)
    : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    inner class ViewHolder(binding: ItemUserRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgUser = binding.imageView
        val tvName = binding.tvName
        val tvUsername = binding.tvUsername
        val tvBio= binding.tvBio
        val tvLocation = binding.tvLocation

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int = listUsers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listUsers[position]
        Glide.with(holder.itemView.context).load(item.avatarUrl).into(holder.imgUser)
        holder.tvUsername.text=item.login
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USER,listUsers[holder.adapterPosition])
            holder.itemView.context.startActivity(intent) }

        val client = ApiConfig.getApiService().getDetailUser(item.login)

        client.enqueue(object : Callback<DetailUserResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<DetailUserResponse>, response: Response<DetailUserResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        holder.tvName.text = responseBody.name
                        holder.tvBio.text = "Bio: ${responseBody.bio}"
                        holder.tvLocation.text = "Loc: ${responseBody.location}"

                    }
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {}
        })
    }
}