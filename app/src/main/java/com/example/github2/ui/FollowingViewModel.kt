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

class FollowingViewModel : ViewModel() {
    private var _listFollowing = MutableLiveData<List<User>?>()
    val listFollowing: MutableLiveData<List<User>?> = _listFollowing

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowingData(login: String?) {
        if (login != null) {
            _isLoading.postValue(true)
            val client = ApiConfig.getApiService().getFollowing(login)
            client.enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    _isLoading.postValue(false)
                    val responseBody = response.body()
                    if (responseBody != null && response.isSuccessful) {
                        _listFollowing.postValue(responseBody)
                    } else {
                        Log.e(TAG, "onResponse: getFollowingData ${response.message()}" )
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    _isLoading.postValue(false)
                    Log.e(TAG, "onFailure: getFollowingData ${t.message}")
                }

            })
        }
    }

    companion object {
        private val TAG = FollowingFragment::class.java.simpleName
    }
}