package com.ceylonsolutions.postmate.base

import android.app.Application
import android.content.Context

class BaseApplication : Application() {

    companion object {
        private lateinit var mInstance: BaseApplication

        fun getApplicationContext() : Context {
            return mInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }
}