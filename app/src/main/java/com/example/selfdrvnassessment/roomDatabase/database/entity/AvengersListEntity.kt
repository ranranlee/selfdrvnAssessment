package com.example.selfdrvnassessment.roomDatabase.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class AvengersListEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "rating")
    var rating: Int,
    @ColumnInfo(name = "imagePath")
    var imagePath: String

)