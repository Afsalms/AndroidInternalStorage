package com.example.qbuser.androidinternalstorage

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.qbuser.androidinternalstorage.db.DatabaseHandler
import com.example.qbuser.androidinternalstorage.models.InputEntries
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var storageChoice = "file system"
    val filename = "test.txt"
    val sharedPreferenceName = "test_pref"
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
        val radionBtn = findViewById<RadioButton>(v.id)
        storageChoice = radionBtn.text.toString()
        Toast.makeText(this, storageChoice, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.save_button -> {
                Toast.makeText(this, "Save button clicked", Toast.LENGTH_SHORT).show()
                saveToLocalStorage()
                inputTextField?.text = ""

            }
            R.id.load_button -> {
                Toast.makeText(this, "Load button clicked", Toast.LENGTH_SHORT).show()
                loadFromLocalStorage()
            }
        }

    }

    fun saveToLocalStorage(){
        loadText?.text = ""
        loadText?.visibility = View.INVISIBLE
        when(storageChoice){
            "file system" -> {
                saveToFileSystem()
            }
            "Shared preference" -> {
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
        var sharedPref = this.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)
        var editor = sharedPref.edit()
        editor.putString("text", inputTextField?.text.toString())
        editor.commit()

    }

    fun saveToSQLite(){
        var dbHandler = DatabaseHandler(this)
        var newData:InputEntries = InputEntries()
        var text = inputTextField?.text.toString()
        newData.text = text
        if (newData.text == null){
            return
        }
        var is_added = dbHandler.addEntry(newData)
        if (is_added){
            Toast.makeText(this, "New record added successfully", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this, "Error please retry after some time", Toast.LENGTH_SHORT).show()
        }

    }

    fun loadFromLocalStorage(){
        when(storageChoice){
            "file system" -> {
                loadFromFileSystem()
            }
            "Shared preference" -> {
                loadFromSharedPreference()
            }
            "SQLite" -> {
                loadFromSQLite()
            }

        }
    }

    fun loadFromFileSystem(){
        var fileObj = File(this.filesDir, filename)
        if (fileObj.exists()){
            var lines = fileObj.readLines()
            var loadedStr = ""
            lines.forEach({loadedStr += it + "\n" })
            populateLoadTextView(loadedStr)

        }
        else{
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT)
        }
    }

    fun loadFromSharedPreference(){
        var sharedPref = this.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)
        var loadedText = sharedPref.getString("text", "default")
        if (loadedText != null && !loadedText.equals("default")){
            populateLoadTextView(loadedText)
        }
    }

    fun loadFromSQLite(){
        var dbhandler = DatabaseHandler(this)
        var saveEntries = dbhandler.getAllEntries()
        var loadText = ""
        for (item in saveEntries){
            loadText += item.text + "\n"
        }
        populateLoadTextView(loadText)

    }

    fun populateLoadTextView(textString: String){
        loadText?.visibility = View.VISIBLE
        loadText?.text = textString

    }



}
