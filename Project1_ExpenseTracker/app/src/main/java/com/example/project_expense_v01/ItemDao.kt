package com.example.project_expense_v01

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDao {

    @Insert
    fun insertItem(items: Item)

    @Query("select * from items order by itemprice desc limit:lt offset :off")
    fun selectItems(lt:Int,off:Int): LiveData<List<Item>>


    @Query("select * from items limit :lt offset :off")
    fun selectLimitItems(lt:Int,off:Int): LiveData<List<Item>>

    @Query("select * from items limit :lt ")
    fun selectLimit(lt:Int): LiveData<List<Item>>

    @Query("select IFNULL(sum(itemPrice),0)  from items")
    fun selectExpense(): LiveData<Double>

    @Query("select IFNULL(count(*),0)  from items")
    fun selectCount(): Int

    @Query("select IFNULL(count(*),0) from items where upper(itemName) like '%' || upper(:searchText) || '%' ")
    fun searchCount(searchText : String):Int


    @Query("select sum(itemPrice) from items where upper(itemName) like '%' || upper(:searchText) || '%'")
    fun searchItemExpense(searchText : String): LiveData<Double>


    @Query("select * from items where upper(itemName) like '%' || upper(:searchText) || '%' limit:lt offset :off")
    fun search(searchText : String,lt: Int, off: Int): LiveData<List<Item>>


    @Query("select * from items order by substr(itemDate,7,4) desc, substr(itemDate,4,2) desc, substr(itemDate,1,2) desc ")
    fun recent(): LiveData<List<Item>>

    @Query("select * from items order by substr(itemDate,7,4) desc, substr(itemDate,4,2) desc, substr(itemDate,1,2) desc limit:lt offset :off")
    fun recentWithLimit(lt:Int,off:Int): LiveData<List<Item>>

    @Query("select * from items order by itemName limit:lt offset :off")
    fun sortByNameWithLimit(lt: Int,off: Int):LiveData<List<Item>>

    @Query("select * from items order by itemName ")
    fun sortByName(): LiveData<List<Item>>

    @Query("select * from items order by itemPrice asc  limit:lt offset :off")
    fun sortLowToHigh(lt: Int,off: Int): LiveData<List<Item>>

    @Query("select * from items order by itemPrice desc  limit:lt offset :off")
    fun sortHighToLow(lt: Int,off: Int): LiveData<List<Item>>

    @Update
    fun updateItems(items: Item)

    @Delete
    fun deleteItems(items: Item)

    @Query("delete from items")
    fun deleteAll()
}



