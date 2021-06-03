package com.example.selfdrvnassessment.roomDatabase.database.helperImpl

import com.example.selfdrvnassessment.roomDatabase.database.AppDatabase
import com.example.selfdrvnassessment.roomDatabase.database.entity.AvengersListEntity
import com.example.selfdrvnassessment.roomDatabase.database.helperImpl.AvengersListHelper

class AvengersListHelperImpl(private val appDatabase: AppDatabase): AvengersListHelper {

    override suspend fun getAllData(): List<AvengersListEntity> = appDatabase.avengerDao().getAllList()

    override suspend fun insertData(model: AvengersListEntity)  = appDatabase.avengerDao().addList(model)

    override suspend fun updateData(model: AvengersListEntity)  = appDatabase.avengerDao().updateList(model)

    override suspend fun deleteData(model: AvengersListEntity) = appDatabase.avengerDao().deleteList(model)

    override suspend fun getFirst10ItemWithLimit(): List<AvengersListEntity> = appDatabase.avengerDao().getFirst10ItemWithLimit()

    override suspend fun getRemainItemWithLimit(id: Int): List<AvengersListEntity> = appDatabase.avengerDao().getRemainItemWithLimit(id)

    override suspend fun getSingleData(id: Int): AvengersListEntity = appDatabase.avengerDao().getSingleData(id)

    override suspend fun updateRating(id: Int,rating:Int) = appDatabase.avengerDao().updateRating(id,rating)




}