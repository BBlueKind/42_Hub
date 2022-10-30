package com.example.hackathon

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import java.io.File
import java.util.ArrayList

class foodDash : AppCompatActivity() {

    private var courseRV: RecyclerView? = null
    private var courseModelArrayList: ArrayList<CourseModel1>? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )
        supportActionBar?.hide()
        setContentView(R.layout.activity_food_dash)


        val addBtn = findViewById<ImageView>(R.id.imageView6)

        addBtn.setOnClickListener{
            val intent = Intent(this,Add_Foods::class.java)
            startActivity(intent)
            finish()
        }

        var swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout2)

        swipeRefreshLayout.setOnRefreshListener {
            courseModelArrayList!!.clear()
            Handler().postDelayed({
                swipeRefreshLayout.isRefreshing = false
            },300)
        }


        courseRV = findViewById(R.id.idRVCourse)
        courseModelArrayList = ArrayList(10)

        fun updateFood(){
            var title = ""
            var descp = ""
            var qty = ""
            var i = 0
            val courseAdapter = CourseAdapter1(this, courseModelArrayList)
            val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            val file = File("/data/data/com.example.hackathon/files/food/")
            file.walkTopDown().forEach{
                println(it)
                if (i >= 1){
                    println(it)
                    var readFiles = File(it.toString())
                    var read =  readFiles.readLines()
                    title = read[0]
                    descp = read[1]
                    qty = read[2]
                    courseModelArrayList!!.add(CourseModel1(title, descp,qty, R.drawable.cutlery))
                }
                i += 1
            }
            courseRV!!.layoutManager = linearLayoutManager
            courseRV!!.adapter = courseAdapter
        }

        updateFood()

    }
}