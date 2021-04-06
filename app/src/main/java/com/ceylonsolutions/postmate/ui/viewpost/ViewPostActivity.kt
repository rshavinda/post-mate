package com.ceylonsolutions.postmate.ui.viewpost

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ceylonsolutions.postmate.R
import com.ceylonsolutions.postmate.base.BaseActivity
import com.ceylonsolutions.postmate.databinding.ActivityViewPostBinding
import com.ceylonsolutions.postmate.data.model.Post
import com.ceylonsolutions.postmate.data.model.Resource
import com.ceylonsolutions.postmate.data.model.User
import com.ceylonsolutions.postmate.data.type.Status
import com.ceylonsolutions.postmate.ui.addpost.AddPostActivity
import com.ceylonsolutions.postmate.util.Helper


class ViewPostActivity : BaseActivity<ActivityViewPostBinding>() {
    companion object {
        private val TAG = ViewPostActivity::class.java.simpleName
        const val EXTRA_POST_DATA = "EXTRA_POST_DATA"
//        const val EXTRA_USER_AVATAR = "EXTRA_USER_AVATAR"
    }

    override fun setViewBinding() = ActivityViewPostBinding.inflate(layoutInflater)

    private var mPostData: Post? = null
    private var mUserData: User? = null
    private lateinit var mViewModel: ViewPostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        Helper.setScreen(this)
        init()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d(TAG, "onBackPressed: ")
        finish()
    }

    private fun init() {
        showLoading()
        mPostData = intent.getParcelableExtra(EXTRA_POST_DATA)

        getViewBinding().tvTitle.text = Helper.toTitleCaseEveryWord(mPostData?.title)
        getViewBinding().tvPostBody.text = Helper.capitalizeFirstLetter(mPostData?.body)

        mViewModel = ViewModelProvider(this).get(ViewPostViewModel::class.java)
        mViewModel.observeUsers()?.observe(this, mObserveUserData)
        mViewModel.observeComment()?.observe(this, mObserveCommentCountData)

        mViewModel.getUserData(mPostData!!.userId)
        mViewModel.getCommentCount(mPostData!!.postId)
    }

    private fun setUserName(user: User?) {

        val username: String = Helper.toTitleCaseEveryWord(user?.username) ?: return

        val content = SpannableString(username)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        getViewBinding().tvUserName.text = content

        getViewBinding().tvUserName.setOnClickListener {
            val intent = Intent(this, AddPostActivity::class.java)
            intent.putExtra(AddPostActivity.EXTRA_USER_DATA, mUserData)
            startActivity(intent)
        }
    }

    private val mObserveUserData: Observer<in Resource<User>> = Observer { resource ->
        hideLoading()
        Log.d(TAG, "Observe User Data: ")

        when (resource.status) {
            Status.SUCCESS -> {
                mUserData = resource.data
                setUserName(resource.data)
            }
            Status.ERROR -> {
                showErrorMessage(resource.message)
            }
            else -> {
            }
        }
    }

    private val mObserveCommentCountData: Observer<in Resource<Int>> = Observer { resource ->
        hideLoading()
        Log.d(TAG, "Observe Comment Count Data:")

        when (resource.status) {
            Status.SUCCESS -> {
                when (resource.data) {
                    0 -> {
                        getViewBinding().tvCommentCount.text = resources.getString(R.string.no_comment)
                    }
                    1 -> {
                        getViewBinding().tvCommentCount.text = resources.getString(R.string.one_comment)
                    }
                    else -> {
                        getViewBinding().tvCommentCount.text = resources.getString(R.string.comment_with_count, resource.data.toString())
                    }
                }
            }
            Status.ERROR -> {
                showErrorMessage(resource.message)
            }
            else -> {
            }
        }
    }
}