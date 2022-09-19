package com.example.github2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.github2.R
import com.example.github2.adapter.SectionPagerAdapter
import com.example.github2.data.remote.response.ResponseDetailUser
import com.example.github2.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailUserViewModel: DetailUserViewModel
    private lateinit var bundle: Bundle
    private var login: String? = null
    private var id: Int? = null
    private var avatar: String? = null

    private fun getDataFromMainActivity() {
        login = intent.getStringExtra(EXTRA_LOGIN)
        id = intent.getIntExtra(EXTRA_ID, 0)
        avatar = intent.getStringExtra(EXTRA_AVATAR)
    }

    private fun setDetailUserData(user: ResponseDetailUser) {
        binding.apply {
            Glide.with(this@DetailUserActivity)
                .load(user.avatar_url)
                .into(photoUserImageview)
            nameTextview.text = user.name
            followersTextview.text = user.followers.toString()
            followingTextview.text = user.following.toString()
            repositoryTextview.text = user.public_repos.toString()
            companyTextview.text = user.company
            locationTextview.text = user.location
            followers.visibility = View.VISIBLE
            following.visibility = View.VISIBLE
            repository.visibility = View.VISIBLE
        }
    }

    private fun setPagerAdapter(bundle: Bundle) {
        val sectionPagerAdapter = SectionPagerAdapter(this@DetailUserActivity, bundle)
        binding.apply {
            viewpager.adapter = sectionPagerAdapter
            //menghubungkan viewpager2 dengan tablayout
            TabLayoutMediator(tabLayout, viewpager) { tab, position ->
                tab.setIcon(TAB_ICONS[position])
            }.attach()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataFromMainActivity()

        supportActionBar!!.title = "Detail Github User"

        detailUserViewModel = ViewModelProvider(this)[DetailUserViewModel::class.java]
        detailUserViewModel.getDetailUser(login)
        detailUserViewModel.detailUser.observe(this) {
            if(it != null) setDetailUserData(it)
        }

        bundle = Bundle().apply {
            putString(EXTRA_LOGIN, login)
        }
        setPagerAdapter(bundle)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val EXTRA_LOGIN = "EXTRA LOGIN"
        const val EXTRA_ID = "EXTRA ID"
        const val EXTRA_AVATAR = "EXTRA AVATAR"
        private val TAB_ICONS = intArrayOf(
            R.drawable.ic_followers,
            R.drawable.ic_following
        )
    }
}