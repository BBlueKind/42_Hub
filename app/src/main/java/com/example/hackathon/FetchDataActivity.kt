package com.example.hackathon

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FetchDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_data)

        val extras = intent.extras
        var value = "soso"
        var test = "aa"
        if (extras != null) {
             value = extras.getString("soso").toString()
            Toast.makeText(this,value,Toast.LENGTH_LONG).show()
            GetInfo().reqUserInfo(test, "hey")
            Handler().postDelayed({
                println(GetInfo.userName)
            },2500)
            //The key argument here must match that used in the other activity
        }else{
            println(value)
        }
    }
}