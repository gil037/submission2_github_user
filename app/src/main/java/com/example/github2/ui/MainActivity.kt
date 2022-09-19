package com.example.github2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github2.R
import com.example.github2.adapter.UserAdapter
import com.example.github2.data.remote.response.User
import com.example.github2.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    private fun setDataUser(listUser: List<User>) {
        val linearLayout = LinearLayoutManager(this)
        val adapter = UserAdapter(listUser, object : UserAdapter.OnItemClickCallback {
            override fun onClick(login: String, id: Int, avatar: String) {
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java).apply {
                    putExtra(DetailUserActivity.EXTRA_LOGIN, login)
                    putExtra(DetailUserActivity.EXTRA_ID, id)
                    putExtra(DetailUserActivity.EXTRA_AVATAR, avatar)
                }
                startActivity(intent)
            }
        })
        binding.itemUserRecyclerview.layoutManager = linearLayout
        binding.itemUserRecyclerview.adapter = adapter
    }

    private fun showLoading(loading: Boolean) {
        binding.apply {
            if (loading)
                progressbar.visibility = View.VISIBLE
            else
                progressbar.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            elevation = 0F
            setDisplayShowTitleEnabled(true)
        }

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.searchingEdittext.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) mainViewModel.getUser(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var job: Job? = null

                job?.cancel()
                if (newText != null && newText.isNotEmpty()) {
                    job = lifecycleScope.launch(Dispatchers.Main) {
                        delay(500)
                        callEnpoint(newText)
                    }
                }
                return true
            }

            private fun callEnpoint(newText: String) {
                mainViewModel.getUser(newText)
            }

        })

        mainViewModel.listItems.observe(this) { setDataUser(it) }
        mainViewModel.isLoading.observe(this) { showLoading(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_setting_menu -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}