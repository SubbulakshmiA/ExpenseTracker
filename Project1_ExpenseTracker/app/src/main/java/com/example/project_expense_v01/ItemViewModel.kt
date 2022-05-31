package com.example.project_expense_v01

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ItemViewModel(app: Application) : AndroidViewModel(app) {

    private val repo: ItemRepository

    ///var allItems : LiveData<List<Item>>?
   // var searchItems: LiveData<List<Item>>?
    var searchText: String = String()

    init {
        repo = ItemRepository(app)
        ////     allItems = repo.getAllItems()
      //  searchItems = repo.search(searchText)
    }


    fun search(searchText: String,lt: Int, off: Int): LiveData<List<Item>>? {
        return repo.search(searchText,lt,off)
    }


    fun recentWithLimit(lt: Int, off: Int): LiveData<List<Item>>? {
        return repo.recentWithLimit(lt, off)
    }


    fun sortLowToHigh(lt: Int, off: Int): LiveData<List<Item>>? {
        return repo.sortLowToHigh(lt, off)
    }

    fun sortHighToLow(lt: Int, off: Int): LiveData<List<Item>>? {
        return repo.sortHighToLow(lt, off)
    }

    fun sortByNameWithLimit(lt: Int, off: Int): LiveData<List<Item>>? {
        return repo.sortByNameWithLimit(lt, off)
    }

    fun selectExpense(): LiveData<Double>? {
        return repo.selectExpense()
    }

    fun selectCount(): Int? {
        return repo.selectCount()
    }
    fun searchCount(searchText:String):Int?{
        return repo.searchCount(searchText)
    }

    fun searchItemExpense(searchText: String): LiveData<Double>? {
        return repo.searchItemExpense(searchText)
    }

    fun insertItem(item: Item) {
        repo.insertItem(item)
    }

    fun updateItem(item: Item) {
        repo.updateItem(item)
    }

    fun deleteItem(item: Item) {
        repo.deleteItem(item)
    }



}

