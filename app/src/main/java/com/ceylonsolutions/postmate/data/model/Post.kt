package com.ceylonsolutions.postmate.data.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull

@Parcelize
@Entity(tableName = "post")
//@Entity(tableName = "post", indices = [Index(value = ["userId"], unique = true)])
data class Post (
    @SerializedName("id")
    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "postId")
    var postId: Int = 0,
    var userId: Int = 0,
    var title: String = "",
    var body: String = "" )
    : Parcelable{
}