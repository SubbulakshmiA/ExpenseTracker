package com.example.project_expense_v01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var vm: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val show_btn: Button = findViewById(R.id.show_btn)
        val debt: TextView = findViewById(R.id.debt_edit)
        vm = ItemViewModel(application)

        vm.selectExpense()?.observe(this, { debt.text = "$ "+ it.toString() })
        show_btn.setOnClickListener {
            val intentMain2 = Intent(this, ActivityExpenseList::class.java)
            startActivity(intentMain2)
        }
        val addButton: Button = findViewById(R.id.add_btn)
        addButton.setOnClickListener {
            val intentMain3 = Intent(this, ActivityAddList::class.java)
            startActivity(intentMain3)


        }


    }
}