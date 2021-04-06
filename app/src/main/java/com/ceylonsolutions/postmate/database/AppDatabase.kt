package com.ceylonsolutions.postmate.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ceylonsolutions.postmate.base.AppConstant
import com.ceylonsolutions.postmate.base.BaseApplication
import com.ceylonsolutions.postmate.data.model.Comment
import com.ceylonsolutions.postmate.data.model.Post
import com.ceylonsolutions.postmate.data.model.User
import com.ceylonsolutions.postmate.util.Converters

@Database(entities = [Post::class, User::class, Comment::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {

        private var mInstance : AppDatabase? = null

        fun getInstance() : AppDatabase {
            if(mInstance == null){
                mInstance = Room.databaseBuilder(
                    BaseApplication.getApplicationContext(),
                    AppDatabase::class.java,
                    AppConstant.DATABASE
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return mInstance as AppDatabase
        }
    }

    abstract fun postDao(): PostDao?

    abstract fun userDao(): UserDao?

    abstract fun commentDao(): CommentDao?
}