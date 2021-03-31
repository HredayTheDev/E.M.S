package com.example.emsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emsapp.R
import com.example.emsapp.models.Employee

import kotlinx.android.synthetic.main.employee_list_layout.view.*

class NotificationAdapter(
        private val employeeList: ArrayList<Employee>,
        private val listener: OnItemClickListener
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.employee_list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee = employeeList[position]

        holder.nameTV.text = employee.userName
        holder.employeeEmailTV.text = employee.userEmail

        holder.notificationBtn.setOnClickListener {
            listener.onItemClick(employee.notificationToken)
        }
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /*fun bindItems(employee: Employee) {
            val nameTV = itemView.employeenameTV
            val employeeEmailTV = itemView.employeeEmailTV
            val notificationBtn = itemView.imageView

           nameTV.text = employee.name
            employeeEmailTV.text = employee.email



        }*/

        val nameTV: TextView = itemView.employeeNameTV
        val employeeEmailTV: TextView = itemView.employeeEmailTV
        val notificationBtn: ImageView = itemView.imageView


    }

    interface OnItemClickListener {
        fun onItemClick(token: String)
    }

}