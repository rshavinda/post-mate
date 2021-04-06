package com.ceylonsolutions.postmate.network

import com.ceylonsolutions.postmate.data.model.Comment
import com.ceylonsolutions.postmate.data.model.Post
import com.ceylonsolutions.postmate.data.model.User

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AppService {

    @GET("posts")
    fun getPosts(): Call<List<Post>?>?

    @GET("comments")
    fun getComments(): Call<List<Comment>?>?

    @GET("users")
    fun getUsers(): Call<List<User>?>?

    @POST("posts")
    fun setPost(@Body post: Post?): Call<Post?>?
}