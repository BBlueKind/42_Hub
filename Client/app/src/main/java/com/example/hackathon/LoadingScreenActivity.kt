package com.example.hackathon

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager

class LoadingScreenActivity : AppCompatActivity() {

    private lateinit var ImageView2: View
    private var shortAnimationDuration: Int = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )
        supportActionBar?.hide()
        setContentView(R.layout.activity_loading_screen)


        ImageView2 = findViewById(R.id.logo)
        ImageView2.visibility = View.GONE

        crossfade()

        Handler().postDelayed({
            val intent = Intent(this, ReceptionActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        },2000)
    }
    private fun crossfade() {
        ImageView2.apply {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.
            animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(null)
        }
    }

}

