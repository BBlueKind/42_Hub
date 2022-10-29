package com.example.hackathon

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
<<<<<<< HEAD
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.navigation.NavigationView
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import de.hdodenhof.circleimageview.CircleImageView
import java.util.ArrayList
=======
import android.view.Window
import android.view.WindowManager
>>>>>>> origin/Hadi

class CafeteriaActivity : AppCompatActivity() {


    lateinit var toggle: ActionBarDrawerToggle


    private var courseRV: RecyclerView? = null
    private var courseModelArrayList: ArrayList<CourseModel>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<< HEAD

=======
>>>>>>> origin/Hadi
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )
        supportActionBar?.hide()
        setContentView(R.layout.activity_cafeteria)


        courseRV = findViewById(R.id.hisRvView)
        courseModelArrayList = ArrayList(5)


        val soso = findViewById<ImageView>(R.id.imageView7)
        soso.visibility = View.INVISIBLE



        val drawerLayout: DrawerLayout = findViewById(R.id.drawerlayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var soso2 = findViewById<CircleImageView>(R.id.soso)
        soso2.setOnClickListener {
            drawerLayout.openDrawer(navView)
        }


        var swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)

        swipeRefreshLayout.setOnRefreshListener {
            courseModelArrayList!!.clear()
            Handler().postDelayed({
                swipeRefreshLayout.isRefreshing = false
            },300)
        }

        val soso1 = findViewById<Button>(R.id.scanBtn)
        soso1.setOnClickListener {

            // val f = FragmentQRScan()
            // val t: FragmentTransaction = supportFragmentManager.beginTransaction()
            // t.replace(R.id.fragment, f).commit()
            val scanner = IntentIntegrator(this)
            scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            scanner.setBeepEnabled(true)
            scanner.initiateScan()
            scanner.setOrientationLocked(false)

        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val result: IntentResult =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

            if (result != null) {
                val courseAdapter = CourseAdapter(this, courseModelArrayList)
                val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                courseModelArrayList!!.add(CourseModel(result.contents
                    , "Jan 10, 2022 10:30Am",R.drawable.bilal))
                courseModelArrayList!!.add(CourseModel("Bilal", "Jan 10, 2022 10:30Am",R.drawable.bilal))
                courseModelArrayList!!.add(CourseModel("Bilal", "Jan 10, 2022 10:30Am",R.drawable.bilal))
                courseModelArrayList!!.add(CourseModel("Bilal", "Jan 10, 2022 10:30Am",R.drawable.bilal))
                courseModelArrayList!!.add(CourseModel("Bilal", "Jan 10, 2022 10:30Am",R.drawable.bilal))
                courseRV!!.layoutManager = linearLayoutManager
                courseRV!!.adapter = courseAdapter



                val soso = findViewById<ImageView>(R.id.imageView7)
                soso.visibility = View.VISIBLE

                if (result.contents == null) {
                } else {

                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}