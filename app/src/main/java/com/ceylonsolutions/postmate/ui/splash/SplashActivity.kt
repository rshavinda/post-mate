package com.ceylonsolutions.postmate.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.ceylonsolutions.postmate.base.BaseActivity
import com.ceylonsolutions.postmate.databinding.ActivitySplashBinding
import com.ceylonsolutions.postmate.ui.main.MainActivity

@Suppress("DEPRECATION")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun setViewBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        Handler().postDelayed(Runnable {
            startActivity(Intent(baseContext, MainActivity::class.java))
            this.finish()
        },1000L)

    }
}