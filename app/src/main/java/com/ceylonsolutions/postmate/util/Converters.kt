package com.ceylonsolutions.postmate.util

import android.location.Address
import androidx.room.TypeConverter
import com.ceylonsolutions.postmate.data.model.Company
import com.ceylonsolutions.postmate.data.model.Geo
import com.google.gson.Gson

class Converters {

    val mGson: Gson = Gson()

    @TypeConverter
    fun fromAddress(value: Address): String {
        return mGson.toJson(value)
    }

    @TypeConverter
    fun toAddress(value: String): Address {
        return mGson.fromJson(value, Address::class.java)
    }

    @TypeConverter
    fun fromCompany(value: Company): String {
        return mGson.toJson(value)
    }

    @TypeConverter
    fun toCompany(value: String): Company {
        return mGson.fromJson(value, Company::class.java)
    }

    @TypeConverter
    fun fromGeo(value: Geo): String {
        return mGson.toJson(value)
    }

    @TypeConverter
    fun toGeo(value: String): Geo {
        return mGson.fromJson(value, Geo::class.java)
    }
}