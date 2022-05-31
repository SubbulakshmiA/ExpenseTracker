package com.example.project_expense_v01

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ActivityAddList : AppCompatActivity() {

    lateinit var vm: ItemViewModel
    val myCalendar = Calendar.getInstance()
    lateinit var itemName: EditText
    lateinit var itemPrice: EditText
    lateinit var date_field_button: Button
    lateinit var itemDate: TextView
    lateinit var itemDateFormat: Date


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_list)
        vm = ItemViewModel(application)


        val save_btn: Button = findViewById(R.id.save_btn)
        val cancel_btn: Button = findViewById(R.id.cancel_btn)
        //  val item_id: TextView = findViewById(R.id.item_id)
        itemDate = findViewById(R.id.date_field)
        itemName = findViewById(R.id.itemName)
        itemPrice = findViewById(R.id.price)
        date_field_button = findViewById(R.id.date_field_button)

        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        Log.d("AddItemListLog", "${year.toString() + " - " + month + " - " + day}")

        date_field_button.setOnClickListener {
            Log.d("AddItemListLog", "A ${year.toString() + " - " + month + " - " + day}")
            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, myear, mmonth, mdayOfMonth ->
                    itemDate.setText("" +  ("0"+mdayOfMonth).takeLast(2) + "/" + ("0"+(mmonth + 1)).takeLast(2) + "/" + myear)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
            itemDateFormat = Date()
            Log.d("AddItemListLog", "B ${year.toString() + " - " + month + " - " + day}")
        }


        save_btn.setOnClickListener {
          if(itemName.text.toString().isEmpty()){
              itemName.setHint("Enter item Name")
              itemName.setHintTextColor(Color.RED)
          }
            if(itemPrice.text.toString().isEmpty()){
                itemPrice.setHint("Enter a valid price")
                itemPrice.setHintTextColor(Color.RED)
            }
          else if( !isNumber( itemPrice.text.toString())){
                itemPrice.setTextColor(Color.RED)
                Toast.makeText(application, "Enter a valid price", Toast.LENGTH_LONG).show()

            }
            if(itemDate.text.toString().isEmpty()){
                itemDate.setHint("Pick a Date")
                itemDate.setHintTextColor(Color.RED)
            }

            val vFieldValidationError: String = fieldValidationError()
            if (vFieldValidationError != "No Error") {
             //   Toast.makeText(application, vFieldValidationError, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val items = (Item(
                null,
               // itemDate.text.toString(),
                itemDate.text.toString(),
                itemName.text.toString(),
                itemPrice.text.toString().toDouble()
            ))
            vm.insertItem(items)

            val intentMain = Intent(this, ActivityExpenseList::class.java)
            startActivity(intentMain)
            val toast =
                Toast.makeText(applicationContext, "Added  ${items.itemName}", Toast.LENGTH_SHORT)
            toast.show()
        }
        cancel_btn.setOnClickListener {
            val intentMain = Intent(this, ActivityExpenseList::class.java)
            startActivity(intentMain)
        }
    }

    private fun fieldValidationError(): String {
        when (true) {
            itemDate.text.toString().isEmpty() -> {
                return "Item Date is required"
            }
            itemName.text.toString().isEmpty() -> {
                return "Item Name is required"
            }
            itemPrice.text.toString().isEmpty() -> {
                return "Item Price is required"
            }

            !isNumber( itemPrice.text.toString()) -> {
                return "Item price is not a valid number"
            }

            else -> {
                return "No Error"
            }
        }
    }

    fun isNumber(s: String): Boolean {
        try {
            s.toInt()
            Log.d("aaa ActivityAddList ","asdf 1")
            return true
        } catch (ex: NumberFormatException) {
            Log.d("aaa ActivityAddList ","asdf 2")
            return false
        }
    }
/*    private fun CheckAllFields(items:Item): Boolean {
        if (items.itemName == null) {
            itemName.setError("This field is required")
            return false
        }
        if (items.itemDate == null) {
            itemDate.setError("This field is required")
            return false
        }
        if (items.itemPrice == 0.0) {
            itemPrice.setError("This field is required")
            return false
        }*/

    // after all validation return true.




}

