package com.example.qbuser.androidinternalstorage

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    var storageChoice = "Shared preference"
    var preferenceName = "mySharedPreference"
    lateinit var sharedpreference:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedpreference = getPreferences(preferenceName, Context.MODE_PRIVATE)
        var inputTextField = findViewById<TextView>(R.id.editText2)

        var saveBtn = findViewById<Button>(R.id.save_button)
        saveBtn.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "save clicked", Toast.LENGTH_SHORT).show()

            var inputText = inputTextField.text
            Toast.makeText(this, storageChoice, Toast.LENGTH_SHORT).show()
            Toast.makeText(this, inputText, Toast.LENGTH_SHORT).show()

        })
        var loadBtn = findViewById<Button>(R.id.load_button)
        loadBtn.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "load ckicked", Toast.LENGTH_SHORT).show()
        })
    }

    fun radioButtonClick(v: View){
        var radionBtn = findViewById<RadioButton>(v.id)
        storageChoice = radionBtn.text.toString()
        Toast.makeText(this, storageChoice, Toast.LENGTH_SHORT).show()
    }
}
