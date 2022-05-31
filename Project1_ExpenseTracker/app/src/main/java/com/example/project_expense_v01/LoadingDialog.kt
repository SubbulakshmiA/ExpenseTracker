package com.example.project_expense_v01

import android.app.Activity
import android.app.AlertDialog

class LoadingDialog (val mActivity: Activity) {
    private lateinit var isdialog : AlertDialog
    fun startLoading(){
        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_layout,null)
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isdialog = builder.create()
        isdialog.show()
    }
    fun isDismiss(){
        isdialog.dismiss()
    }
}