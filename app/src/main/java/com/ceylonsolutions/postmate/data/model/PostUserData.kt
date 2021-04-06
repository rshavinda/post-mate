package com.ceylonsolutions.postmate.data.model

import android.os.Parcelable
import androidx.room.Embedded
import com.ceylonsolutions.postmate.base.AppConstant
import com.ceylonsolutions.postmate.util.Helper
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostUserData (
        var userId: Int,
        var postId: Int,
        var id: Int,
        var title: String,
        var body: String,
        var name: String,
        var username: String,
        var email: String,
        var phone: String,
        var website: String,
        var company: Company,
        @Embedded
    var address: Address)
    : Parcelable{
    init { }

    fun getEmailMD5Hash():String{
        if(email.isEmpty()){
            return AppConstant.BASE_URL_AVATAR + "00000000000000000000000000000000"
        }
        return AppConstant.BASE_URL_AVATAR + Helper.generateMD5Hash(email)
    }

    fun setPost(post : Post?){
        if (post != null) {
            postId = post.postId
        }
        if (post != null) {
            userId= post.userId
        }
        if (post != null) {
            title= post.title
        }
        if (post != null) {
            body= post.body
        }
    }

    fun setUser(user: User?){
        if (user != null) {
            name= user.name
        }
        if (user != null) {
            username= user.username
        }
        if (user != null) {
            email= user.email
        }
        if (user != null) {
            phone= user.phone
        }
        if (user != null) {
            website = user.website
        }
        if (user != null) {
            company= user.company
        }
        if (user != null) {
            address = user.address
        }
    }
}
