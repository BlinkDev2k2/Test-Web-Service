package com.blink.testwebservice.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.blink.testwebservice.R
import com.blink.testwebservice.model.Student
import java.text.SimpleDateFormat

class StudentAdapter(private val context: Context, private val data: ArrayList<Student>, private val listener: ButtonItemClickListener) : BaseAdapter(){
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SimpleDateFormat")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var v = convertView
        val viewHolder: ViewHolder
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.item_list_student, parent, false)
            viewHolder = ViewHolder(v)
            viewHolder.bind()
            v.tag = viewHolder
        } else {
            viewHolder = v.tag as ViewHolder
        }
        viewHolder.id.text = data[position].id.toString()
        viewHolder.name.text = data[position].name
        viewHolder.address.text = data[position].address
        viewHolder.birthDate.text = SimpleDateFormat("dd/MM/yyyy").format(data[position].birthDate)
        viewHolder.btnUpdate.setOnClickListener {
            listener.onUpdateClicked(data[position].id)
        }
        viewHolder.btnDelete.setOnClickListener {
            listener.onDelClicked(data[position].id)
        }
        return v!!
    }

    interface ButtonItemClickListener {
        fun onUpdateClicked(id: Short)
        fun onDelClicked(id: Short)
    }

    companion object {
        internal class ViewHolder(private val view: View) {
            internal lateinit var id: TextView
            internal lateinit var name: TextView
            internal lateinit var address: TextView
            internal lateinit var birthDate: TextView
            internal lateinit var btnUpdate: ImageButton
            internal lateinit var btnDelete: ImageButton

            fun bind() {
                id = view.findViewById(R.id.tvID)
                name = view.findViewById(R.id.tvName)
                address = view.findViewById(R.id.tvAddress)
                birthDate = view.findViewById(R.id.tvBirthDate)
                btnUpdate = view.findViewById(R.id.btnEditStudent)
                btnDelete = view.findViewById(R.id.btnDelStudent)
            }
        }
    }
}