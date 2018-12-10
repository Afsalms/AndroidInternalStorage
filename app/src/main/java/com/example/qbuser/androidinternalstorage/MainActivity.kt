package com.example.qbuser.androidinternalstorage

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.io.File

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var storageChoice = "file system"
    val filename = "test.txt"
    var inputTextField: TextView? = null
    var loadText:TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inputTextField = findViewById<TextView>(R.id.editText2)
        loadText = findViewById<TextView>(R.id.textView2)

        var saveBtn = findViewById<Button>(R.id.save_button)
        saveBtn.setOnClickListener(this)
        var loadBtn = findViewById<Button>(R.id.load_button)
        loadBtn.setOnClickListener(this)
    }

    fun radioButtonClick(v: View){
        var radionBtn = findViewById<RadioButton>(v.id)
        storageChoice = radionBtn.text.toString()
        Toast.makeText(this, storageChoice, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.save_button -> {
                Toast.makeText(this, "Save button clicked", Toast.LENGTH_SHORT).show()
                saveToFileSystem()
                inputTextField?.text = ""

            }
            R.id.load_button -> {
                Toast.makeText(this, "Load buttin clicked", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun saveToLocalStorage(){
        when(storageChoice){
            "file system" -> {
                saveToFileSystem()
            }
            "shared preference" -> {
                saveToSharedPreference()
            }
            "SQLite" -> {
                saveToSQLite()
            }
        }
    }

    fun saveToFileSystem(){
        var fileObj = File(this.filesDir, filename)
        if(fileObj.exists()){
            fileObj.appendText(inputTextField?.text.toString()+"\n")
        }else{
            fileObj.writeText(inputTextField?.text.toString() + "\n")
        }
    }

    fun saveToSharedPreference(){
        TODO()
    }

    fun saveToSQLite(){
        TODO()
    }



}
