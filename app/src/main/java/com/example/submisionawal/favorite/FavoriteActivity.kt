package com.example.submisionawal.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submisionawal.R
import com.example.submisionawal.adaptor.FavoriteAdapter
import com.example.submisionawal.data.ItemsItem
import com.example.submisionawal.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteUserViewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Favorite User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initObserver()

    }

    private fun initObserver() {
        favoriteUserViewModel.getFavoriteUsers().observe(this) { users ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val user = ItemsItem(it.login, it.avatarUrl.toString())
                items.add(user)
            }

            if (users.isEmpty()) {
                setFavoriteUsers(items)
            } else {
                setFavoriteUsers(items)
            }
        }
    }

    private fun setFavoriteUsers(favoriteUsers: ArrayList<ItemsItem>) {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = FavoriteAdapter(favoriteUsers)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}