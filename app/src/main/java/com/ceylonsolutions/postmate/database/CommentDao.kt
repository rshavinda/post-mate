package com.ceylonsolutions.postmate.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ceylonsolutions.postmate.data.model.Comment
import io.reactivex.Completable

@Dao
interface CommentDao {

    @Query("SELECT * FROM comment")
    fun getAll(): LiveData<List<Comment?>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(comments: List<Comment?>?): Completable?

    @Query("DELETE FROM comment")
    fun deleteAll(): Completable?

    @Query("SELECT COUNT(*) FROM comment WHERE postId =:id")
    fun getCount(id: Int): LiveData<Int>?

}