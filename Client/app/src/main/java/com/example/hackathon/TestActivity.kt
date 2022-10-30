package com.example.hackathon

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class TestActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )
        supportActionBar?.hide()

        setContentView(R.layout.activity_test)

        val myWebView = findViewById<WebView>(R.id.webview)
        myWebView.settings.javaScriptEnabled = true;
        myWebView.loadUrl("https://api.intra.42.fr/oauth/authorize?client_id=u-s4t2ud-c1f54e74b2fd222ef23b182e098e490cf854e1e5984d17b5253ebea8b5a87224&redirect_uri=https%3A%2F%2Fwww.google.com&response_type=code")

        myWebView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                if (url != null) {
                    if (url.contains("?code")) {
                        val intent = Intent(this@TestActivity, MainActivity::class.java)
                        intent.putExtra("soso", url.subSequence(29, url.length))
                        intent.putExtra("url", url)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }
        }
    }
}