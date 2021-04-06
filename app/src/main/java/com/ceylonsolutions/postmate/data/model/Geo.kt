package com.ceylonsolutions.postmate.data.model

import android.os.Parcelable
import androidx.room.TypeConverters
import com.ceylonsolutions.postmate.util.Converters
import kotlinx.android.parcel.Parcelize

@Parcelize
@TypeConverters(Converters::class)
data class Geo(
    var lat: String,
    var lng: String
) : Parcelable