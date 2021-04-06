package com.ceylonsolutions.postmate.network

import com.ceylonsolutions.postmate.base.AppConstant
import com.ceylonsolutions.postmate.data.model.Comment
import com.ceylonsolutions.postmate.data.model.Post
import com.ceylonsolutions.postmate.data.model.User

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppRepository() {
    companion object {
        private var mInstance : AppRepository? = null
        private var appService: AppService? = null

        fun getInstance() : AppRepository {
            if(mInstance == null){
                mInstance = AppRepository ()
            }
            return mInstance as AppRepository
        }
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        appService =  retrofit.create(AppService::class.java)
    }

    fun getPosts(): Call<List<Post>?>? {
        return appService?.getPosts()
    }

    fun getComments(): Call<List<Comment>?>? {
        return appService?.getComments()
    }

    fun getUsers(): Call<List<User>?>? {
        return appService?.getUsers()
    }

    fun setPost(post: Post): Call<Post?>? {
        return appService?.setPost(post)
    }
}