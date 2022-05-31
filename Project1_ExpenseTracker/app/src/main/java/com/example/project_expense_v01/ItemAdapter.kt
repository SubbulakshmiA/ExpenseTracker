package com.example.project_expense_v01

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter (private val onCardClick:(position:Int)->Unit,private var itemList: List<Item> ) : RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate a view and return it
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)
        return  ViewHolder(view,onCardClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // add current item to the holder
        val item = itemList[position]
        holder.itemName.text = item.itemName
        holder.itemDate.text = item.itemDate
        holder.itemPrice.text = item.itemPrice.toString()
    }

    override fun getItemCount(): Int {
        // return the size of the datasource
        return itemList.size
    }
    fun setItems(itemList:List<Item>){
        this.itemList=itemList
        notifyDataSetChanged()
    }


}


class ViewHolder(view: View, private val onCardClick:(position:Int)->Unit): RecyclerView.ViewHolder(view),
    View.OnClickListener{

    init {
        itemView.setOnClickListener (this)
    }

    val itemName: TextView = view.findViewById(R.id.itemName)
    val itemPrice: TextView = view.findViewById(R.id.price)
    val itemDate: TextView = view.findViewById(R.id.date)

    override fun onClick(v: View?) {
        val position =adapterPosition
        Log.d("position","$position")
        onCardClick(position)
    }
}
