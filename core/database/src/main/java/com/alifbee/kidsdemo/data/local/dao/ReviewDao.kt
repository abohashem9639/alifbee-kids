package com.alifbee.kidsdemo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.alifbee.kidsdemo.data.local.entity.ReviewEntity

@Dao
interface ReviewDao {

    @Query("SELECT * FROM reviews")
    suspend fun getAll(): List<ReviewEntity>

    @Query("SELECT COUNT(*) FROM reviews")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(reviews: List<ReviewEntity>)

    @Query("DELETE FROM reviews")
    suspend fun clear()

    @Query("UPDATE reviews SET isFavorite = :favorite WHERE id = :id")
    suspend fun setFavorite(id: Int, favorite: Boolean)

    @Transaction
    suspend fun replaceAll(reviews: List<ReviewEntity>) {
        clear()
        insertAll(reviews)
    }
}
