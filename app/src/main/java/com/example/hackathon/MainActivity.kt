package com.example.hackathon

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)


        var imageSlider = findViewById<ImageSlider>(R.id.v_flipper)
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.image12,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.calendar, ScaleTypes.CENTER_CROP))
        imageSlider.setImageList(imageList)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerlayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var soso1 = findViewById<CircleImageView>(R.id.soso)
        soso1.setOnClickListener {
            drawerLayout.openDrawer(navView)
        }
        var textViewResult = findViewById<TextView>(R.id.textView3)
        GetInfo().reqUserInfo(textViewResult)
        //Toast.makeText(this, test, Toast.LENGTH_LONG).show()

    }
}