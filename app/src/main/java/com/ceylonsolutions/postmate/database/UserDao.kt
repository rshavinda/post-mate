package com.ceylonsolutions.postmate.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ceylonsolutions.postmate.data.model.User
import io.reactivex.Completable

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<User?>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User?>?): Completable?

    @Query("DELETE FROM user")
    fun deleteAll(): Completable?

    @Query("SELECT * FROM user WHERE id = :userId LIMIT 1")
    fun getUserById(userId: Int): LiveData<User>?

    @Query("SELECT * FROM user WHERE id = :userId LIMIT 1")
    fun getUser(userId: Int): User?
}