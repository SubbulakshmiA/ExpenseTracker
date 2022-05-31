package com.example.project_expense_v01

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ActivityExpenseList : AppCompatActivity() {

    lateinit var itemList: ArrayList<Item>
    private var limit: Int = 4
    private var pageNo: Int = 0
    private var loadScreenMode: String = ""
    private var numberOfRows: Int? = 0
    private var maxNumberOfPages: Double = 0.0
    private var searchNumberOfRows : Int? =0

    private lateinit var vm: ItemViewModel
    private lateinit var adapter: ItemAdapter
    private lateinit var total: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_list)
        vm = ItemViewModel(application)
        numberOfRows = vm.selectCount()
        maxNumberOfPages =  (numberOfRows!!.toDouble() / limit)

        debug("maxNumberOfPages","${maxNumberOfPages}")
            println("searchNumberOfRows  $searchNumberOfRows")
        val floating_btn: FloatingActionButton = findViewById(R.id.floating_btn)
        val back_btn: Button = findViewById(R.id.back_btn)
        val search_icon: View = findViewById(R.id.search_icon)
        val search_bar: TextView = findViewById(R.id.search_bar)
        val loadmore_btn: Button = findViewById(R.id.loadmore_btn)
        val loadless_btn: Button = findViewById(R.id.loadless_btn)

        total = findViewById(R.id.total)
        if (pageNo == 0) {
            Log.d("aaaoverride","code loadless_btn.setVisibility(INVISIBLE)")
            loadless_btn.setVisibility(INVISIBLE)
        }
        if ( maxNumberOfPages <= 1 ){
            loadmore_btn.setVisibility(GONE)
        }
        if ( maxNumberOfPages > 1 ){
            loadmore_btn.setVisibility(VISIBLE)
        }
        val textview: TextView = findViewById(R.id.sort_text_view)
        val sort = resources.getStringArray(R.array.sort_by)
        val spinner: Spinner = findViewById(R.id.spinner)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, sort
        )
        spinner.adapter = adapter
        search_icon.setOnClickListener {
            var searchText = search_bar.text.toString()
            searchNumberOfRows = vm.searchCount(searchText)
            maxNumberOfPages =  (searchNumberOfRows!!.toDouble() / limit)
            if ( maxNumberOfPages <= 1 ){
                loadmore_btn.setVisibility(GONE)
            }
            if ( maxNumberOfPages > 1 ){
                loadmore_btn.setVisibility(VISIBLE)
            }
            loadScreenMode= "Search"
            loadScreen( loadScreenMode, searchText, 0)
//            vm.search(searchText)

        }

        floating_btn.setOnClickListener {
            val intentMain3 = Intent(this, ActivityAddList::class.java)
            startActivity(intentMain3)
        }
        back_btn.setOnClickListener {
            val intentMain3 = Intent(this, MainActivity::class.java)
            startActivity(intentMain3)
        }

        loadmore_btn.setOnClickListener(View.OnClickListener {
            val loading = LoadingDialog(this)
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    loading.isDismiss()
                }
            }, 500)

            pageNo = pageNo + 1
            loadScreen(loadScreenMode, "", pageNo)
            if (pageNo >= maxNumberOfPages) {
                loadmore_btn.setVisibility(GONE)
            }
            if (pageNo < maxNumberOfPages) {
                loadless_btn.setVisibility(VISIBLE)
            }

        })
        loadless_btn.setOnClickListener(View.OnClickListener {
            val loading = LoadingDialog(this)
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    loading.isDismiss()
                }
            }, 500)

            pageNo = pageNo - 1
            loadScreen(loadScreenMode, "", pageNo)
            if (pageNo < maxNumberOfPages) {
                loadmore_btn.setVisibility(VISIBLE)
            }
            if (pageNo == 0 ) {
                loadless_btn.setVisibility(INVISIBLE)
            }

        })

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                loadScreenMode = sort[position].toString()
                loadScreen(loadScreenMode, "", pageNo)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


    }

    fun onCardClick(position: Int) {
        val intentMain3 = Intent(this, ActivityEditList::class.java)
        intentMain3.putExtra("itemId", itemList[position].itemId)
        intentMain3.putExtra("itemDate", itemList[position].itemDate)
        intentMain3.putExtra("itemName", itemList[position].itemName)
        intentMain3.putExtra("itemPrice", itemList[position].itemPrice)
        startActivity(intentMain3)
    }

    fun getItems(itemList: List<Item>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        adapter.notifyDataSetChanged()
    }

    fun loadScreen(v: String, searchString1: String, pageNumber: Int) {
        vm = ItemViewModel(application)
        itemList = ArrayList()
        when (v) {
            "Search" -> {
                vm.search(searchString1,limit, (pageNumber * limit))?.observe(this, { itemList -> getItems(itemList) })
                vm.searchItemExpense(searchString1)?.observe(this, { total.setText("$ "+it.toString()) })
            }
            "Recent" -> {
                vm.recentWithLimit(limit, (pageNumber * limit))
                    ?.observe(this, { itemList -> getItems(itemList) })
                vm.selectExpense()?.observe(this, { total.setText( "$ "+ it.toString()) })

                }

            "Item Name" -> {
                vm.sortByNameWithLimit(limit, (pageNumber * limit))?.observe(this, { itemList -> getItems(itemList) })
                   }
            "Low to High" -> {
                vm.sortLowToHigh(limit, (pageNumber * limit))?.observe(this, { itemList -> getItems(itemList) })
            }
            "High to Low" -> {
                vm.sortHighToLow(limit, (pageNumber * limit))?.observe(this, { itemList -> getItems(itemList) })
            }

        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ItemAdapter({ position -> onCardClick(position) }, itemList)
        recyclerView.adapter = adapter

       }
    fun debug(function: String, logMessage: String) {
        Log.d("aaa"+function,logMessage )
    }


}
