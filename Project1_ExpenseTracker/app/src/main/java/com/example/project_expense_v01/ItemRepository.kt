package com.example.project_expense_v01

import android.content.Context
import androidx.lifecycle.LiveData

class ItemRepository(context: Context) {

    var db: ItemDao? = AppDatabase.getInstance(context)?.itemDao()

    fun recentWithLimit(lt: Int, off: Int): LiveData<List<Item>>? {
        return db?.recentWithLimit(lt, off)
    }


    fun sortHighToLow(lt: Int, off: Int): LiveData<List<Item>>? {
        return db?.sortHighToLow(lt, off)
    }

    fun sortLowToHigh(lt: Int, off: Int): LiveData<List<Item>>? {
        return db?.sortLowToHigh(lt, off)
    }
    fun sortByNameWithLimit(lt: Int, off: Int): LiveData<List<Item>>? {
        return db?.sortByNameWithLimit(lt, off)
    }

    fun search(searchText: String,lt: Int, off: Int): LiveData<List<Item>>? {
        return db?.search(searchText,lt,off)
    }
    fun selectExpense(): LiveData<Double>? {
        return db?.selectExpense()
    }

    fun selectCount(): Int? {
        return db?.selectCount()
    }
    fun searchCount(searchText:String):Int?{
        return db?.searchCount(searchText)
    }

    fun searchItemExpense(searchText: String): LiveData<Double>? {
        return db?.searchItemExpense(searchText)
    }

    fun insertItem(items: Item) {
        db?.insertItem(items)
    }

    fun updateItem(items: Item) {
        db?.updateItems(items)
    }

    fun deleteItem(items: Item) {
        db?.deleteItems(items)
    }
}