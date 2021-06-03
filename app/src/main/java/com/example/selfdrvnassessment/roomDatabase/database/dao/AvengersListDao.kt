package com.example.selfdrvnassessment.roomDatabase.database.dao

import androidx.room.*
import com.example.selfdrvnassessment.roomDatabase.database.entity.AvengersListEntity

@Dao
interface AvengersListDao {

    //get all data from local db
    @Query("Select * from AvengersListEntity ")
    suspend fun getAllList(): List<AvengersListEntity>

    //add data to local db
    @Insert
    suspend fun addList(param: AvengersListEntity)

    @Update
    suspend fun updateList(param: AvengersListEntity)

    //remove data from local db
    @Delete
    suspend fun deleteList(param: AvengersListEntity)


    @Query ("update AvengersListEntity set rating = :rating where id = :id")
    suspend fun updateRating(id:Int,rating:Int)

    //get first 10 data frm local db with limited data with ascending order
    @Query("Select * from AvengersListEntity  order by id asc limit 10")
    suspend fun getFirst10ItemWithLimit(): List<AvengersListEntity>

    //get remain data frm local db with limited data with ascending order
    @Query("Select * from AvengersListEntity where  id > :id order by id asc limit 10")
    suspend fun getRemainItemWithLimit(id: Int): List<AvengersListEntity>

    //get single data from db
    @Query("Select * from AvengersListEntity where id = :id")
    suspend fun getSingleData(id: Int): AvengersListEntity



}