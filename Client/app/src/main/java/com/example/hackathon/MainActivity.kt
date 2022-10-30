package com.example.hackathon

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Display
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.cardview.widget.CardView
import androidx.drawerlayout.widget.DrawerLayout
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    lateinit var qrIV: ImageView

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

        qrIV = findViewById(R.id.imageView4)




        val ttext1 = findViewById<TextView>(R.id.textView3)
        val ttext2 = findViewById<TextView>(R.id.textView4)
        val image1 = findViewById<ImageView>(R.id.imageView2)
        val extras = intent.extras
        if (extras != null) {
            var value = extras.getString("soso").toString()
            Toast.makeText(this,value, Toast.LENGTH_LONG).show()
            GetInfo(this).reqUserInfo(ttext1,ttext2,image1,qrIV,value)
            ttext1.visibility = View.VISIBLE
            ttext2.visibility = View.VISIBLE
            image1.visibility = View.VISIBLE
            //generateQR(ttext1.text.toString(),ttext2.text.toString())
            //The key argument here must match that used in the other activity
        }

        val viewall = findViewById<TextView>(R.id.textView2)

        viewall.setOnClickListener {
            //intent
            val intent = Intent(this,events::class.java)
            startActivity(intent)
            //startActivity
        }


        val footballbtn = findViewById<CardView>(R.id.playBtn2)
        val foodbtn = findViewById<CardView>(R.id.thingsToDoBtn)

        foodbtn.setOnClickListener {

        }



        footballbtn.setOnClickListener {
            val intent = Intent(this,footexpand::class.java)
            startActivity(intent)
        }

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

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> drawerLayout.closeDrawers()
                R.id.nav_alain -> drawerLayout.closeDrawers()
                R.id.nav_capital -> drawerLayout.closeDrawers()
                R.id.nav_capital1 -> Toast.makeText(this,"123",Toast.LENGTH_LONG).show()
            }
            true
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var soso1 = findViewById<CircleImageView>(R.id.soso)
        soso1.setOnClickListener {
            drawerLayout.openDrawer(navView)
        }


    }
}