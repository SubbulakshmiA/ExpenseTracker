package com.example.project_expense_v01

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "items")
data class Item(@PrimaryKey(autoGenerate = true) var itemId:Int?,
                @ColumnInfo(name = "itemDate") var itemDate: String?,
                @ColumnInfo(name = "itemName")var itemName:String?,
                @ColumnInfo(name = "itemPrice")var itemPrice:Double?) {
}