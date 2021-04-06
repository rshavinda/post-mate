package com.ceylonsolutions.postmate.util

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

class Helper {
    companion object{
        fun generateMD5Hash(input:String): String {
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }

         fun setScreen(activity: Activity) {
            activity.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setWindowFlag(activity,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
                activity.window.statusBarColor = Color.TRANSPARENT
            }
             else
                setWindowFlag(activity,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
         }

        /**
         * Capitalize First Letter of the sentence
         *
         * @param text - string value
         * @return formatted text
         */
        fun capitalizeFirstLetter(text: String?): String? {
            var text = text
            return try {
                if (text == null || text.trim { it <= ' ' }.isEmpty()) return ""
                text = text.trim { it <= ' ' }
                if (text.length > 1) text.substring(0, 1)
                    .toUpperCase(Locale.US) + text.substring(1)
                    .toLowerCase(Locale.US) else text.toUpperCase()
            } catch (e: Exception) {
                text
            }
        }

        /**
         * Capitalize First Letter of every word in the text
         *
         * @param text - string value
         * @return formatted text
         */
        fun toTitleCaseEveryWord(text: String?): String? {
            return try {
                if (text == null || text.trim { it <= ' ' }.isEmpty()) return ""
                val words =
                    text.trim { it <= ' ' }.split(" ").toTypedArray()
                val result = StringBuilder()
                for (word in words) {
                    result.append(
                        if (word.length > 1) word.substring(0, 1)
                            .toUpperCase(Locale.US) + word.substring(1)
                            .toLowerCase(Locale.US) else word.toUpperCase()
                    ).append(" ")
                }
                result.toString().trim { it <= ' ' }
            } catch (e: Exception) {
                text
            }
        }

        private fun setWindowFlag(activity: Activity,bits: Int, on: Boolean) {
            val win = activity.window
            val winParams = win.attributes
            if (on) {
                winParams.flags = winParams.flags or bits
            } else {
                winParams.flags = winParams.flags and bits.inv()
            }
            win.attributes = winParams
        }
    }
}