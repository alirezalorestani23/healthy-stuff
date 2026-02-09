package com.example.healthystuff.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.healthystuff.data.local.entity.FoodEntryEntity
import com.example.healthystuff.data.local.entity.FoodItemEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface FoodLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodEntry(foodEntry: FoodEntryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodItem(foodItems: List<FoodItemEntity>)

    @Update
    suspend fun updateFoodEntry(foodEntry: FoodEntryEntity)


    @Query("DELETE FROM food_items WHERE entryId = :entryId")
    suspend fun deleteItemsForFoodEntry(entryId: Long)

    @Query("DELETE FROM food_entries WHERE id = :entryId")
    suspend fun deleteFoodEntry(entryId: Long)

    @Query(
        "SELECT * FROM food_entries " +
                "WHERE timestampMillis >= :startMillis AND timestampMillis < :endMillis " +
                "ORDER BY timestampMillis DESC"
    )
    fun observeEntriesBetween(startMillis: Long, endMillis: Long): Flow<List<FoodEntryEntity>>

    @Query("SELECT * FROM food_entries WHERE id = :entryId LIMIT 1")
    suspend fun getFoodEntry(entryId: Long): FoodEntryEntity?

    @Query("SELECT * FROM food_items WHERE entryId = :entryId")
    suspend fun getItemsForFoodEntry(entryId: Long): List<FoodItemEntity>

}