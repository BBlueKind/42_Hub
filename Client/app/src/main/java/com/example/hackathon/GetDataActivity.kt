package com.example.hackathon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast

class GetDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_data)
        val extras = intent.extras
        if (extras != null) {
            var value = extras.getString("soso").toString()
            Toast.makeText(this,value, Toast.LENGTH_LONG).show()
            GetInfo().reqUserInfo(value)
            Handler().postDelayed({
                                  println("kotlin" + GetInfo.userName)
            },6000)
            //The key argument here must match that used in the other activity
        }
    }
}