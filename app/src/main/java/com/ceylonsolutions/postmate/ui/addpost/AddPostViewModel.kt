package com.ceylonsolutions.postmate.ui.addpost

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.ceylonsolutions.postmate.data.model.Post
import com.ceylonsolutions.postmate.data.model.Resource
import com.ceylonsolutions.postmate.network.AppRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AddPostViewModel: ViewModel() {
    companion object {
        private val TAG = AddPostViewModel::class.java.simpleName
    }
    var responseData: MediatorLiveData<Resource<Post?>> = MediatorLiveData<Resource<Post?>>()

    fun observeResponse(): MediatorLiveData<Resource<Post?>> {
        return responseData
    }

    fun sendPost(post : Post) {
        Log.d(TAG, "sendPost: > $post")

        AppRepository.getInstance().setPost(post)?.enqueue(object : Callback<Post?> {
            override fun onResponse(call: Call<Post?>, response: Response<Post?>) {
                if (response.code() == 201) {
                    Log.d(TAG, "sendPost: "+response.code()+" < " + response.body().toString())

                    responseData.value  = Resource.success(data = response.body())
                }
                else{
                    responseData.value  =  Resource.error(data = null, message = "Something went wrong please try again!")
                }
            }
            override fun onFailure(call: Call<Post?>, t: Throwable) {
                if (t is IOException) {
                    responseData.value  =  Resource.noInternet(data = null, message = "No Internet!")
                }
            }
        })
    }
}