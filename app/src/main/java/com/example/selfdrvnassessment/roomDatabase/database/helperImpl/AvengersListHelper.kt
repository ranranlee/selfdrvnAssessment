package com.example.selfdrvnassessment.roomDatabase.database.helperImpl

import com.example.selfdrvnassessment.roomDatabase.database.entity.AvengersListEntity


interface AvengersListHelper {

    suspend fun getAllData(): List<AvengersListEntity>
    suspend fun insertData(model: AvengersListEntity)
    suspend fun updateData(model: AvengersListEntity)
    suspend fun deleteData(model: AvengersListEntity)
    suspend fun getFirst10ItemWithLimit(): List<AvengersListEntity>
    suspend fun getRemainItemWithLimit(id:Int): List<AvengersListEntity>
    suspend fun getSingleData(id: Int): AvengersListEntity
    suspend fun updateRating(id: Int,rating:Int)

}