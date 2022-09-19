package com.example.github2.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.github2.ui.FollowersFragment
import com.example.github2.ui.FollowingFragment

class SectionPagerAdapter(
    context: AppCompatActivity,
    private val bundle: Bundle) : FragmentStateAdapter(context) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }

        if (fragment != null) fragment.arguments = bundle
        return fragment as Fragment
    }
}