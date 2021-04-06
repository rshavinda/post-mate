package com.ceylonsolutions.postmate.ui.main

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.ceylonsolutions.postmate.data.model.*
import com.ceylonsolutions.postmate.database.AppDatabase
import com.ceylonsolutions.postmate.network.AppRepository
import com.ceylonsolutions.postmate.ui.viewpost.ViewPostViewModel
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException


class MainViewModel : ViewModel() {
    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    var postData: MediatorLiveData<Resource<List<Post?>?>> = MediatorLiveData<Resource<List<Post?>?>>()
    var responseData: MediatorLiveData<Resource<Int>> = MediatorLiveData<Resource<Int>>()


    fun observePosts(): MediatorLiveData<Resource<List<Post?>?>> {
        return postData
    }

    fun observeResponse(): MediatorLiveData<Resource<Int>>? {
        return responseData
    }

    override fun onCleared() {
        AppDatabase.getInstance().postDao()?.getAll()?.removeObserver(mObservePosts)
        super.onCleared()
    }

    fun getPosts() {
        AppRepository.getInstance().getPosts()?.enqueue(object : Callback<List<Post>?> {
            override fun onResponse(call: Call<List<Post>?>, response: Response<List<Post>?>) {
                Log.d(TAG, "onResponse: Code " + response.code())
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: " + (response.body()?.size ?: 0))

                    postData.value  = Resource.success(data = response.body())

                        AppDatabase.getInstance().postDao()?.deleteAll()?.andThen(
                        AppDatabase.getInstance().postDao()?.insertAll(response.body()))
                        ?.subscribeOn(Schedulers.io())
                        ?.subscribe(object : DisposableCompletableObserver() {

                                override fun onComplete() {
                                    Log.d(TAG, "getPosts : onComplete: ")


                                }

                                override fun onError(e: Throwable) {
                                    Log.e(TAG, "getPosts : onError: ",e )
                                }
                            })
                }
            }
            override fun onFailure(call: Call<List<Post>?>, t: Throwable) {
                Log.e(TAG, "onFailure: ",t.cause )
                if (t is IOException) {
                    responseData.value  =  Resource.noInternet(data = null, message = "No Internet!")
                }
                else{
                    postData.value  =  Resource.error(data = null, message = "No Post Data!")
                }
            }
        })
    }


    fun getCachedPosts(){
        Log.d(TAG, "getCachedPosts: ")
        AppDatabase.getInstance().postDao()?.getAll()?.observeForever(mObservePosts)
    }

    fun getUsers() {
        AppRepository.getInstance().getUsers()?.enqueue(object : Callback<List<User>?> {
            override fun onResponse(call: Call<List<User>?>, response: Response<List<User>?>) {
                if (response.code() == 200) {
                    Log.d(TAG, "getUsers: " + (response.body()?.size ?: 0))


                    AppDatabase.getInstance().userDao()?.deleteAll()?.andThen(
                        AppDatabase.getInstance().userDao()?.insertAll(response.body()))
                        ?.subscribeOn(Schedulers.io())
                        ?.subscribe(object : DisposableCompletableObserver() {

                            override fun onComplete() {
                                Log.d(TAG, "getUsers: onComplete: ")
                            }

                            override fun onError(e: Throwable) {
                                Log.e(TAG, "getUsers: onError: ",e )
                            }
                        })
                }
            }
            override fun onFailure(call: Call<List<User>?>, t: Throwable) {
                if (t is IOException || t is UnknownHostException) {
                    responseData.value  =  Resource.noInternet(data = null, message = "No Internet!")
                }
            }
        })
    }

    fun getComments() {

        AppRepository.getInstance().getComments()?.enqueue(object : Callback<List<Comment>?> {
            override fun onResponse(call: Call<List<Comment>?>, response: Response<List<Comment>?>) {
                if (response.code() == 200) {
                    Log.d(TAG, "getComments: " + (response.body()?.size ?: 0))


                    AppDatabase.getInstance().commentDao()?.deleteAll()?.andThen(
                        AppDatabase.getInstance().commentDao()?.insertAll(response.body()))
                        ?.subscribeOn(Schedulers.io())
                        ?.subscribe(object : DisposableCompletableObserver() {

                            override fun onComplete() {
                                Log.d(TAG, "getComments: onComplete: ")
                            }

                            override fun onError(e: Throwable) {
                                Log.e(TAG, "getComments: onError: ",e )
                            }
                        })
                }
            }
            override fun onFailure(call: Call<List<Comment>?>, t: Throwable) {
                if (t is IOException) {
                    responseData.value  =  Resource.noInternet(data = null, message = "No Internet!")
                }
            }
        })
    }

    private val mObservePosts: Observer<in List<Post?>?> = Observer { resource ->
        Log.d(TAG, "ObserveUserData: ")
        if (resource != null) {
            postData.value = Resource.success(data = resource)
        } else {
            postData.value = Resource.error(data = null, message = "No User Data!")
        }
    }
}

