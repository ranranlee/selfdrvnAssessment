package com.example.selfdrvnassessment.roomDatabase.database

import android.content.Context
import com.example.selfdrvnassessment.roomDatabase.database.AppDatabase
import com.example.selfdrvnassessment.roomDatabase.database.entity.AvengersListEntity
import com.example.selfdrvnassessment.roomDatabase.database.helperImpl.AvengersListHelperImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseHelper {

    //get all data from local db
    fun fetchAllList(context: Context,func:(List<AvengersListEntity>)-> Unit){
        val dbHelper = AvengersListHelperImpl(AppDatabase.getInstance(context))
        CoroutineScope(Dispatchers.Main).launch {
            val list = dbHelper.getAllData()
            func.invoke(list)
        }
    }

    //get first 10 data frm local db with limited data with ascending order
    fun getFirst10ItemWithLimit(context: Context, func: (List<AvengersListEntity>) -> Unit) {
        val dbHelper = AvengersListHelperImpl(AppDatabase.getInstance(context))
        CoroutineScope(Dispatchers.Main).launch {
            val list = dbHelper.getFirst10ItemWithLimit()
            func.invoke(list)
        }
    }

    //get remain data frm local db with limited data with ascending order
    fun getRemainItemWithLimit(context: Context, id:Int, func: (List<AvengersListEntity>) -> Unit) {
        val dbHelper = AvengersListHelperImpl(AppDatabase.getInstance(context))
        CoroutineScope(Dispatchers.Main).launch {
            val list = dbHelper.getRemainItemWithLimit(id)
            func.invoke(list)
        }
    }

    //add data to local db
    fun addTodoList(context: Context, params: AvengersListEntity) {
        val dbHelper = AvengersListHelperImpl(AppDatabase.getInstance(context))
        CoroutineScope(Dispatchers.Main).launch {
            dbHelper.insertData(params)
        }
    }

    //remove data from local db
    fun removeTodoList(context: Context,params: AvengersListEntity){
        val dbHelper = AvengersListHelperImpl(AppDatabase.getInstance(context))
        CoroutineScope(Dispatchers.Main).launch {
            dbHelper.deleteData(params)
        }
    }



    //get single data from db
    fun getSingleData(context: Context, id:Int, func: (AvengersListEntity) -> Unit, insert:() ->Unit){
        val dbHelper = AvengersListHelperImpl(AppDatabase.getInstance(context))
        CoroutineScope(Dispatchers.Main).launch {
            val data = dbHelper.getSingleData(id)
            if (data == null){
                insert.invoke()
            }else{
                func.invoke(data)
            }

        }
    }

    fun updateStatus(context: Context,id: Int,rating:Int){
        val dbHelper = AvengersListHelperImpl(AppDatabase.getInstance(context))
        CoroutineScope(Dispatchers.Main).launch {
          dbHelper.updateRating(id,rating)

        }
    }

}