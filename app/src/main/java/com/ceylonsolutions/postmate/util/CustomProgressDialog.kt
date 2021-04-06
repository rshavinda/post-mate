package com.ceylonsolutions.postmate.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import com.ceylonsolutions.postmate.R
import kotlinx.android.synthetic.main.layout_progress_dialog.view.*

class CustomProgressDialog {
    lateinit var dialog: CustomDialog

    fun init(context: Context): Dialog {
        return init(context, null)
    }

    fun init(context: Context, title: CharSequence?): Dialog {
        val inflater = (context as Activity).layoutInflater
        val view = inflater.inflate(R.layout.layout_progress_dialog, null)
        if (title != null) {
            view.textViewMessage.text = title
        }

        // Card Color
//        view.cardViewBg.setCardBackgroundColor(context.resources.getColor(R.color.white))

        // Progress Bar Color
        setColorFilter(view.progressBar.indeterminateDrawable, ResourcesCompat.getColor(context.resources, R.color.purple_700, null))

        // Text Color
//        view.textViewMessage.setTextColor(Color.BLACK)

        dialog = CustomDialog(context)
        dialog.setContentView(view)
        return dialog
    }

    fun show(){
        if(dialog == null) return
        dialog.show()
    }

    fun dismiss(){
        if(dialog == null) return
        dialog.dismiss()
    }

    fun isShowing():Boolean{
        if(dialog == null) return false
       return dialog.isShowing
    }

    private fun setColorFilter(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    @Suppress("DEPRECATION")
    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    class CustomDialog(context: Context) : Dialog(context, R.style.CustomDialogTheme) {
        init {
            window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                insets.consumeSystemWindowInsets()
            }
        }
    }
}