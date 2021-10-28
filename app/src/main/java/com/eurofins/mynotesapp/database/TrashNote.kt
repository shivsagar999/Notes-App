package com.eurofins.mynotesapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trashTable")
class TrashNote (@ColumnInfo(name = "Title") var noteTitle: String,
                 @ColumnInfo(name = "description") var noteDescription: String,
                 @ColumnInfo(name = "timestamp") var timeStamp: String
                 ){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 1
}
