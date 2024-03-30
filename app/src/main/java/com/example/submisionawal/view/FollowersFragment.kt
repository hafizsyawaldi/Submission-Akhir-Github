package com.example.submisionawal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submisionawal.R
import com.example.submisionawal.adaptor.UserListAdapter
import com.example.submisionawal.data.ItemsItem
import com.example.submisionawal.model.FollowersViewModel


class FollowersFragment : Fragment() {
    private lateinit var adapter: UserListAdapter
    private lateinit var recyclerView: RecyclerView
    private val followerViewModel by viewModels<FollowersViewModel>()

    companion object {
        const val ARG_USERNAME = "username"
        const val ARG_POSITION = "section_number"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME).toString()
        val position = arguments?.getInt(ARG_POSITION)

        if (position == 1){
            getFollower(view,username)
        } else {
            getFollowing(view,username)

        }
        initRecyclerView(view)
    }

    private fun initRecyclerView(view: View) {
        adapter = UserListAdapter(arrayListOf())
        recyclerView = view.findViewById(R.id.rvFollower)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }
    private fun getFollower(view: View, username: String){
        followerViewModel.getFollowers(username)
        followerViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it, view)
        }
        followerViewModel.follower.observe(viewLifecycleOwner){
            if (it != null){
                setUsersData(it)
            }
        }
    }

    private fun getFollowing(view: View, username: String){
        followerViewModel.getFollowing(username)
        followerViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it, view)
        }
        followerViewModel.following.observe(viewLifecycleOwner){
            if (it != null){
                setUsersData(it)
            }
        }
    }
    private fun showLoading(isLoading: Boolean, view: View) {
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun setUsersData(users: ArrayList<ItemsItem>) {
        adapter.setData(users)
    }
}