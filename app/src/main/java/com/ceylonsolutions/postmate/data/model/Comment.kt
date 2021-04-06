package com.ceylonsolutions.postmate.data.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "comment")
@Parcelize
data class Comment(
    @NonNull
    @PrimaryKey(autoGenerate = true) var commentNumber: Int,
    var postId: String,
    var id: String,
    var name: String,
    var email: String,
    var body: String
) : Parcelable