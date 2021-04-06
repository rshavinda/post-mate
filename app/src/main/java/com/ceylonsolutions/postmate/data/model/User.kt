package com.ceylonsolutions.postmate.data.model


import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.*
import com.ceylonsolutions.postmate.data.model.Address
import com.ceylonsolutions.postmate.data.model.Company
import com.ceylonsolutions.postmate.util.Converters
import kotlinx.android.parcel.Parcelize
/*
    foreignKeys = [ForeignKey(entity = Post::class,
        parentColumns = arrayOf("userId"),
        childColumns = arrayOf("id"),
        onDelete = ForeignKey.CASCADE)]*/
@Entity(tableName = "user")
@Parcelize
@TypeConverters(Converters::class)
data class User (
        @NonNull
    @PrimaryKey
    var id: Int,
        var name: String,
        var username: String,
        var email: String,
        var phone: String,
        var website: String,
        var company: Company,
        @Embedded
    var address: Address

) : Parcelable {
    @Ignore
    fun getFullAddressString(): String {
        return "\nSuite : ${this.address.suite}" +
                "\nStreet : " + this.address.street +
                "\nCity : " + this.address.city +
                "\nZip-Code : " + this.address.zipcode

    }
}
