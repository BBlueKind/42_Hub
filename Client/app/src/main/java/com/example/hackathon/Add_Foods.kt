package com.example.hackathon

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EdgeEffect
import android.widget.EditText
import java.io.File

class Add_Foods : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )
        supportActionBar?.hide()
        setContentView(R.layout.activity_add_foods)




        fun saveInFiles(Title: String, Description: String, Quantity: String){

            val folder = filesDir

            val f = File(folder, "food")
            if (!f.exists()){
                f.mkdir()
            }
            val createFile = File("/data/data/com.example.hackathon/files/food/$Title.txt")
            if (!createFile.exists()){
                createFile.createNewFile()
                createFile.writeText("$Title\n$Description\n$Quantity")
            }
        }

        val addBtn = findViewById<Button>(R.id.addFoodBtn)
        var title = findViewById<EditText>(R.id.textView5)
        var description = findViewById<EditText>(R.id.editTextTextMultiLine)
        val qty = findViewById<EditText>(R.id.editTextTextPersonName)

        addBtn.setOnClickListener {
            saveInFiles(title.text.toString(),description.text.toString(),qty.text.toString())
            val intent = Intent(this,foodDash::class.java)
            finish()
            startActivity(intent)
        }
    }
}