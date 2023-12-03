package com.blink.testwebservice.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.blink.testwebservice.R
import java.text.SimpleDateFormat
import java.util.Calendar

class AddStudentActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        var date: String

        val edtBirth = findViewById<EditText>(R.id.edtBirthDate)
        edtBirth.setOnClickListener {
            DatePickerDialog(this, { _, year, month, dayOfMonth ->
                val ca = Calendar.getInstance()
                ca.set(year, month, dayOfMonth)
                date = SimpleDateFormat("yyyy-MM-dd").format(ca.time)
                edtBirth.setText(date)
            }, 2002, 5, 19).show()
        }

        findViewById<Button>(R.id.btnCancel).setOnClickListener { finish() }

        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            val intent = Intent()
            intent.putExtra(MainActivity.EXTRA_NAME, findViewById<EditText>(R.id.edtName).text.toString().trim())
            intent.putExtra(MainActivity.EXTRA_ADDRESS, findViewById<EditText>(R.id.edtAddress).text.toString().trim())
            intent.putExtra(MainActivity.EXTRA_BIRTH, findViewById<EditText>(R.id.edtBirthDate).text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}