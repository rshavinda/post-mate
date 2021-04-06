package com.ceylonsolutions.postmate.ui.viewpost

import android.util.Log
import androidx.lifecycle.*
import com.ceylonsolutions.postmate.data.model.Resource
import com.ceylonsolutions.postmate.database.AppDatabase
import com.ceylonsolutions.postmate.data.model.User

class ViewPostViewModel : ViewModel() {
    companion object {
        private val TAG = ViewPostViewModel::class.java.simpleName
    }

    var userData: MediatorLiveData<Resource<User>> = MediatorLiveData<Resource<User>>()
    var commentData: MediatorLiveData<Resource<Int>> = MediatorLiveData<Resource<Int>>()

    fun observeUsers(): MediatorLiveData<Resource<User>>? {
        return userData
    }

    fun observeComment(): MediatorLiveData<Resource<Int>>? {
        return commentData
    }

    override fun onCleared() {
        AppDatabase.getInstance().userDao()?.getUserById(0)?.removeObserver(mObserveUserData)
        AppDatabase.getInstance().commentDao()?.getCount(0)?.removeObserver(mCommentCountData)
        super.onCleared()
    }

    fun getUserData(userId: Int) {
        Log.d(TAG, "getUserData: $userId")

        AppDatabase.getInstance().userDao()?.getUserById(userId)?.observeForever(mObserveUserData)
    }

    fun getCommentCount(postId: Int) {
        Log.d(TAG, "getUserData: $postId")

        AppDatabase.getInstance().commentDao()?.getCount(postId)?.observeForever(mCommentCountData)
    }

    private val mObserveUserData: Observer<in User> = Observer { resource ->
        Log.d(TAG, "ObserveUserData: ")
        if (resource != null) {
            userData.value = Resource.success(data = resource)
        } else {
            userData.value = Resource.error(data = null, message = "No User Data!")
        }
    }

    private val mCommentCountData: Observer<in Int> = Observer { resource ->
        Log.d(TAG, "CommentCountData: ")
        if (resource != null) {
            commentData.value = Resource.success(data = resource)
        } else {
            commentData.value = Resource.error(data = null, message = "No Comment Data!")
        }
    }
}