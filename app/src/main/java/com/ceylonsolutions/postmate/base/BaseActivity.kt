package com.ceylonsolutions.postmate.base

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.ceylonsolutions.postmate.R
import com.ceylonsolutions.postmate.util.CustomProgressDialog

abstract class BaseActivity< Binding : ViewBinding> : AppCompatActivity() {

    private lateinit var mBinding : Binding
    private val mProgressDialog = CustomProgressDialog()
    abstract fun setViewBinding(): Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = setViewBinding()
        setContentView(mBinding.root)
        mProgressDialog.init(this)
    }

    fun getViewBinding():Binding{
        return mBinding
    }

    fun showMessage(text : String){
        if(text.isEmpty())
        Toast.makeText(applicationContext, text, LENGTH_LONG).show()
    }

    fun showErrorMessage(text : String?){
        if(text == null || text.isEmpty()) {
            Toast.makeText(applicationContext, getString(R.string.error_default), LENGTH_LONG).show()
            return
        }
        Toast.makeText(applicationContext, text, LENGTH_LONG).show()
    }

    fun showMessage(resId : Int){
        Toast.makeText(applicationContext, resId, LENGTH_LONG).show()
    }

    fun showLoading(){
        if(!mProgressDialog.isShowing()) {
            mProgressDialog.show()
        }
    }

    fun hideLoading(){
        if(mProgressDialog.isShowing()) {
            mProgressDialog.dismiss()
        }
    }
}