package com.ceylonsolutions.postmate.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ceylonsolutions.postmate.data.model.Post
import io.reactivex.Completable

@Dao
interface PostDao {

    @Query("SELECT * FROM post")
    fun getAll(): LiveData<List<Post?>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<Post?>?): Completable?

    @Query("DELETE FROM post")
    fun deleteAll(): Completable?

//    @Transaction
//    @Query("SELECT * FROM post LEFT JOIN user ON post.userId = user.id")
//    fun getAllUserPostData(): LiveData<List<PostUserData>>
}