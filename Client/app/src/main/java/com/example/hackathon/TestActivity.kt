package com.example.hackathon

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class TestActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)


        val mywebView = findViewById<WebView>(R.id.webview)
        mywebView.loadUrl("https://www.google.com")

        finish()
    }
}