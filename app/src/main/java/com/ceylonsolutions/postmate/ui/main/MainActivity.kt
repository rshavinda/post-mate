package com.ceylonsolutions.postmate.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ceylonsolutions.postmate.R
import com.ceylonsolutions.postmate.base.BaseActivity
import com.ceylonsolutions.postmate.databinding.ActivityMainBinding
import com.ceylonsolutions.postmate.data.model.Post
import com.ceylonsolutions.postmate.data.model.Resource
import com.ceylonsolutions.postmate.data.type.Status
import com.ceylonsolutions.postmate.ui.addpost.AddPostActivity
import com.ceylonsolutions.postmate.ui.main.adapter.PostAdapter
import com.ceylonsolutions.postmate.ui.viewpost.ViewPostActivity
import com.ceylonsolutions.postmate.util.Helper

@Suppress("DEPRECATION")
class MainActivity : BaseActivity<ActivityMainBinding>(), PostAdapter.ItemClickListener {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
    override fun setViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    private lateinit var mViewModel: MainViewModel
    private lateinit var mAdapter : PostAdapter
    private lateinit var mAlertDialogInternet: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        Helper.setScreen(this)
        init()
    }

    override fun onResume() {
        super.onResume()
        if(mAdapter.itemCount == 0){
            callApi()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    override fun onPostClick(post: Post?) {
        val intent = Intent(this, ViewPostActivity::class.java)
        intent.putExtra(ViewPostActivity.EXTRA_POST_DATA, post)
        startActivity(intent)
    }

    private fun init() {
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mViewModel.observePosts().observe(this, mObservePostData)
        mViewModel.observeResponse()?.observe(this, mObserveResponses)
        initInternetConnectionErrorDialog()

        mAdapter = PostAdapter( this)
        getViewBinding().recyclerViewPosts.layoutManager = LinearLayoutManager(baseContext)
        getViewBinding().recyclerViewPosts.adapter = mAdapter

        getViewBinding().buttonAdd.setOnClickListener {
            startActivity(Intent(this, AddPostActivity::class.java))
        }

        mViewModel.getCachedPosts()
    }

    private fun callApi(){
        showLoading()
        mViewModel.getPosts()
        mViewModel.getUsers()
        mViewModel.getComments()
    }


    private fun initInternetConnectionErrorDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.no_internet)
        builder.setMessage(R.string.no_internet_error)
        builder.setIcon(R.drawable.ic_information)

        builder.setPositiveButton(R.string.button_retry){ dialogInterface, which ->
            Handler().postDelayed({
                callApi()
            },3000L)
            dialogInterface.dismiss()
        }

        builder.setNeutralButton(R.string.button_cancel){dialogInterface , which ->
            dialogInterface.dismiss()
        }

        mAlertDialogInternet = builder.create()
        mAlertDialogInternet.setCancelable(false)
    }

    private val mObservePostData: Observer<in Resource<List<Post?>?> > = Observer { resource ->
        Log.d(TAG, "ObservePostData : ")
        Handler().postDelayed({
            hideLoading()
        },500L)

        when (resource.status) {
            Status.SUCCESS -> {
                Log.d(TAG, "ObservePostData : List Size : "+ (resource.data?.size ?: 0 ))
                mAdapter.addPostList(resource.data)
            }
            Status.ERROR -> {
                showErrorMessage(resource.message)
            }
            Status.NO_INTERNET -> {
                showErrorMessage(resource.message)
            }
            else -> {
            }
        }
    }

    private val mObserveResponses: Observer<in Resource<Int>> = Observer { resource ->
        hideLoading()
        when (resource.status) {
            Status.SUCCESS -> {
            }
            Status.ERROR -> {
                showErrorMessage(resource.message)
            }
            Status.NO_INTERNET -> {
                if (!mAlertDialogInternet.isShowing) {
                    mAlertDialogInternet.show()
                }
            }
            else -> {
            }
        }
    }
}