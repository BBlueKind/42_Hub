package com.example.hackathon

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.navigation.NavigationView
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CafeteriaActivity : AppCompatActivity() {


    lateinit var toggle: ActionBarDrawerToggle


    private var courseRV: RecyclerView? = null
    private var courseModelArrayList: ArrayList<CourseModel>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        fun openfood(){
            val intent = Intent(this,Add_Foods::class.java)
            startActivity(intent)
        }


        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_request -> Toast.makeText(this,"Coming Soon!",Toast.LENGTH_SHORT).show()
                R.id.nav_logOut -> Toast.makeText(this,"Coming Soon!",Toast.LENGTH_SHORT).show()
                R.id.nav_capital -> openfood()
            }
            true
        }



        fun getCheckIns(){
            var name = ""
            var date = ""
            var time = ""
            var i = 0
            val courseAdapter = CourseAdapter(this, courseModelArrayList)
            val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            val file = File("/data/data/com.example.hackathon/files/reports/")
            file.walkTopDown().forEach{
                println(it)
                if (i >= 1){
                    println(it)
                    var readFiles = File(it.toString())
                    var read =  readFiles.readLines()
                    name = read[0]
                    date = read[1]
                    courseModelArrayList!!.add(CourseModel(name, date, R.drawable._2ad1))
                }
                i += 1
            }
            courseRV!!.layoutManager = linearLayoutManager
            courseRV!!.adapter = courseAdapter
        }


        var swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)

        swipeRefreshLayout.setOnRefreshListener {
            courseModelArrayList!!.clear()
            Handler().postDelayed({
                getCheckIns()
                swipeRefreshLayout.isRefreshing = false
            },300)
        }

        val viewAll = findViewById<TextView>(R.id.textView2)

        viewAll.setOnClickListener{
            //val intent
            val intent = Intent(this,Cafeteria_Download_View::class.java)
            startActivity(intent)
            //start activity
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
            scanner.setOrientationLocked(true)

        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val result: IntentResult =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

            if (result != null) {
                val sdf = SimpleDateFormat("dd/M/yyyy HH:mm:ss")
                val time = DateFormat.getTimeInstance()
                val time2 = time.format(Date())
                val date = sdf.format(Date())
                val soso1 = result.contents.split("#")
                saveInFiles(soso1[0],time2,date)
                val soso = findViewById<ImageView>(R.id.imageView7)
                soso.visibility = View.VISIBLE
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
    private fun saveInFiles(Name: String, Date: String, Time: String){

        val folder = filesDir

        val f = File(folder, "reports")
        if (!f.exists()){
            f.mkdir()
        }
        val createFile = File("/data/data/com.example.hackathon/files/reports/$Name.txt")
        if (!createFile.exists()){
            createFile.createNewFile()
            createFile.writeText("$Name\n$Date\n$Time")
        }else{
            Toast.makeText(this,"This Student Has Already Taken A Box",Toast.LENGTH_SHORT).show()
        }
    }
}