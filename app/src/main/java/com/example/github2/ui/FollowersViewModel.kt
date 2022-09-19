package com.example.github2.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github2.data.remote.response.User
import com.example.github2.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    private var _listFollowers = MutableLiveData<List<User>?>()
    val listUser: MutableLiveData<List<User>?> = _listFollowers

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowersData(login: String?) {
        if (login != null) {
            _isLoading.postValue(true)
            val client = ApiConfig.getApiService().getFollowers(login)
            client.enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    _isLoading.postValue(false)
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        _listFollowers.postValue(responseBody)
                    } else {
                        Log.e(TAG, "onResponse getDataFollowers: ${response.message()}", )
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    _isLoading.postValue(false)
                    Log.e(TAG, "onFailure getDataFollowers: ${t.printStackTrace()}")
                }
            })
        }
    }

    companion object {
        private val TAG = FollowersViewModel::class.java.simpleName
    }
}