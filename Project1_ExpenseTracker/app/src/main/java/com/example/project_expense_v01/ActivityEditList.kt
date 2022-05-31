package com.example.project_expense_v01

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class ActivityEditList : AppCompatActivity() {

    lateinit var vm : ItemViewModel
    val myCalendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_list)

        vm = ItemViewModel(application)
        val update_btn : Button = findViewById(R.id.update_btn)
        val delete_btn : Button = findViewById(R.id.delete_btn)
        val cancel_btn : Button = findViewById(R.id.cancel_btn)


        val itemId: TextView = findViewById(R.id.item_id)
        val itemName: EditText = findViewById(R.id.itemName)
        val itemDate: EditText = findViewById(R.id.date)
        val itemPrice: EditText = findViewById(R.id.price)

        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        Log.d("AddItemListLog", "${year.toString() + " - " + month + " - " + day}")
        val date_field_button: Button = findViewById(R.id.date_field_button)


        itemId.setText(intent.getIntExtra("itemId",0).toString())
         //      itemDate.setText(intent.getStringExtra("itemDate"))
        itemDate.setText(intent.getStringExtra("itemDate"))
        itemName.setText(intent.getStringExtra("itemName"))
        itemPrice.setText(intent.getDoubleExtra("itemPrice",0.0).toString())

        date_field_button.setOnClickListener {
            Log.d("AddItemListLog", "A ${year.toString() + " - " + month + " - " + day}")
            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, myear, mmonth, mdayOfMonth ->
                    itemDate.setText("" + mdayOfMonth + "/" + (mmonth + 1) + "/" + myear)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
            Log.d("AddItemListLog", "B ${year.toString() + " - " + month + " - " + day}")
        }



        update_btn.setOnClickListener {

            val items =(Item(itemId.text.toString().toInt(), itemDate.text.toString(),itemName.text.toString(),
               itemPrice.text.toString().toDouble()))
            println("items  $items")
            vm.updateItem(items)
            val intentMain= Intent(this,ActivityExpenseList::class.java)
            startActivity(intentMain)
            val toast = Toast.makeText(applicationContext, "Updated  ${items.itemName}",
                Toast.LENGTH_SHORT)
            toast.show()

        }

        delete_btn.setOnClickListener {

            val items =(Item(itemId.text.toString().toInt(), itemDate.text.toString(),itemName.text.toString(),
                itemPrice.text.toString().toDouble()))
            vm.deleteItem(items)
            val intentMain= Intent(this,ActivityExpenseList::class.java)
            startActivity(intentMain)
            val toast = Toast.makeText(applicationContext, "Deleted  ${items.itemName}",
                Toast.LENGTH_SHORT)
            toast.show()
        }

        cancel_btn.setOnClickListener {
            val intentMain= Intent(this,ActivityExpenseList::class.java)
            startActivity(intentMain)
        }



    }
}