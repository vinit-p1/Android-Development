package com.example.miniproject1

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.miniproject1.Model.Store
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDAO {
    @Query("SELECT * FROM allstores")
    fun getStores(): Flow<List<Store>>

    @Query("SELECT * FROM allstores WHERE id=:id")
    fun getItem(id:Long): Flow<Store>

    @Insert
    suspend fun insertItem(item:Store) : Long

    @Update
    suspend fun updateItem(item:Store)

    @Delete
    suspend fun deleteItem(item:Store)

    @Query("DELETE FROM allstores WHERE id = :id")
    suspend fun deleteItem(id:Long)
}