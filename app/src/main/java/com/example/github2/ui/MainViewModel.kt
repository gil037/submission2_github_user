package com.example.github2.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github2.data.remote.response.ResponseUser
import com.example.github2.data.remote.response.User
import com.example.github2.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _listItems = MutableLiveData<List<User>>()
    val listItems: LiveData<List<User>> = _listItems

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(keyword: String) {
        _isLoading.postValue(true)
        val client = ApiConfig.getApiService().getUser(keyword)
        client.enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                _isLoading.postValue(false)
                val responseBody = response.body()
                if (responseBody != null && response.isSuccessful) {
                    _listItems.postValue(responseBody.items)
                } else {
                    Log.e(TAG, "onResponse getUser(): ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                _isLoading.postValue(false)
                Log.e(TAG, "onFailure getUser(): ${t.printStackTrace()}")
            }

        })
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }
}