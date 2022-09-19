package com.example.github2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github2.adapter.FollowersFollowingAdapter
import com.example.github2.data.remote.response.User
import com.example.github2.databinding.FragmentFollowersFollowingBinding

class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowersFollowingBinding? = null
    private val binding get() = _binding

    private var login: String? = null
    private lateinit var viewModel: FollowersViewModel

    private fun getDataFromSectionPagerAdapter() {
        login = arguments?.getString(DetailUserActivity.EXTRA_LOGIN)
    }

    private fun setFollowersData(listUser: List<User>) {
        val linearLayout = LinearLayoutManager(requireActivity())
        val followersFollowingAdapter = FollowersFollowingAdapter(listUser)

        binding?.apply {
            itemUserRecyclerview.layoutManager = linearLayout
            itemUserRecyclerview.adapter = followersFollowingAdapter
        }
    }

    private fun setLoading(loading: Boolean) {
        binding?.apply {
            if (loading)
                progressbar.visibility = View.VISIBLE
            else
                progressbar.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersFollowingBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataFromSectionPagerAdapter()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowersViewModel::class.java]
        viewModel.getFollowersData(login)
        viewModel.listUser.observe(viewLifecycleOwner) {
            if (it != null) {
                setFollowersData(it)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { setLoading(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}