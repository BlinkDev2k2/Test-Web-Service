package com.blink.testwebservice.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request.Method
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.blink.testwebservice.R
import com.blink.testwebservice.adapter.StudentAdapter
import com.blink.testwebservice.model.Student
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity(), StudentAdapter.ButtonItemClickListener {
    private lateinit var data: ArrayList<Student>
    private lateinit var adapter: StudentAdapter
    private lateinit var listItem: ListView
    private lateinit var btnAddItem: FloatingActionButton
    private val addStudentResult  = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK && it.data != null) {
            addNewStudent(
                it.data?.getStringExtra(EXTRA_NAME) ?: "Error", it.data?.getStringExtra(
                    EXTRA_ADDRESS
                ) ?: "Error", it.data?.getStringExtra(
                EXTRA_BIRTH
                ) ?: "Error")
        } else {
            Toast.makeText(this, "Canceled Add New Student", Toast.LENGTH_SHORT).show()
        }
    }
    private val updateStudentResult  = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK && it.data != null) {
            updateStudent(it.data?.getShortExtra(EXTRA_RECEIVE_ID, 0) ?: 0,
                it.data?.getStringExtra(EXTRA_NAME) ?: "Error", it.data?.getStringExtra(
                    EXTRA_ADDRESS
                ) ?: "Error", it.data?.getStringExtra(
                    EXTRA_BIRTH
                ) ?: "Error")
        } else {
            Toast.makeText(this, "Canceled Update Student", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SimpleDateFormat", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listItem = findViewById(R.id.listItem)
        btnAddItem = findViewById(R.id.btnAddItem)

        val queue = Volley.newRequestQueue(this)
        queue.add(JsonArrayRequest(
            Method.GET, "https://blinktestapi.000webhostapp.com/getdata.php", null,
            {
                data = ArrayList()
                val len = it.length() - 1
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                for (i in 0..len) {
                    val st = it.getJSONObject(i)
                    val date = dateFormat.parse(st.getString("birthDate"))
                    data.add(
                        Student(st.getInt("id").toShort(), st.getString("name"),
                        st.getString("address"), date)
                    )
                }
                adapter = StudentAdapter(this, data, this)
                listItem.adapter = adapter
            },
            {
                Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
            }))

        btnAddItem.setOnClickListener {
            addStudentResult.launch(Intent(this, AddStudentActivity::class.java))
        }
    }

    private fun addNewStudent(name: String, address: String, birth: String) {
        val queue = Volley.newRequestQueue(this)
        val request = object : StringRequest(Method.POST, "https://blinktestapi.000webhostapp.com/insertdata.php",
            {
                updateUI()
                Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
            },
            {
                Toast.makeText(this, "Add new student to database error", Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val dataMap = HashMap<String, String>()
                dataMap["name"] = name
                dataMap["address"] = address
                dataMap["birthDate"] = birth
                return dataMap
            }
        }
        queue.add(request)
    }

    private fun updateStudent(id: Short, name: String, address: String, birth: String) {
        val queue = Volley.newRequestQueue(this)
        val request = object : StringRequest(Method.POST, "https://blinktestapi.000webhostapp.com/updatedata.php",
            {
                updateUI()
                Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
            },
            {
                Toast.makeText(this, "Update student to database error", Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val dataMap = HashMap<String, String>()
                dataMap["id"] = id.toString()
                dataMap["name"] = name
                dataMap["address"] = address
                dataMap["birthDate"] = birth
                return dataMap
            }
        }
        queue.add(request)
    }

    private fun updateUI() {
        val queue = Volley.newRequestQueue(this)
        queue.add(JsonArrayRequest(
            Method.GET, "https://blinktestapi.000webhostapp.com/getdata.php", null,
            {
                data.clear()
                val len = it.length() - 1
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                for (i in 0..len) {
                    val st = it.getJSONObject(i)
                    val date = dateFormat.parse(st.getString("birthDate"))
                    data.add(
                        Student(st.getInt("id").toShort(), st.getString("name"),
                        st.getString("address"), date)
                    )
                }
                adapter.notifyDataSetChanged()
            },
            {
                Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
            }))
    }

    override fun onUpdateClicked(id: Short) {
        val intent = Intent(this, UpdateStudentActivity::class.java)
        intent.putExtra(EXTRA_SEND_ID, id)
        updateStudentResult.launch(intent)
    }

    override fun onDelClicked(id: Short) {
        AlertDialog.Builder(this).setTitle("Do you want to delete this student")
            .setPositiveButton("yes") { dialog, which ->
                val queue = Volley.newRequestQueue(this)
                val request = object : StringRequest(Method.POST,
                    "https://blinktestapi.000webhostapp.com/deletedata.php",
                    {
                        updateUI()
                        Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                    },
                    {
                        Toast.makeText(this, "Update student to database error", Toast.LENGTH_LONG)
                            .show()
                    }) {
                    override fun getParams(): MutableMap<String, String> {
                        val dataMap = HashMap<String, String>()
                        dataMap["id"] = id.toString()
                        return dataMap
                    }
                }
                queue.add(request)
            }.setNegativeButton("no") { dialog, which ->
                dialog.dismiss()
            }.show()
    }

    companion object {
        const val EXTRA_BIRTH = "ffert"
        const val EXTRA_NAME = "qertx"
        const val EXTRA_ADDRESS = "weqdf"
        const val EXTRA_SEND_ID = "jiejd"
        const val EXTRA_RECEIVE_ID = "jitrejd"
    }
}