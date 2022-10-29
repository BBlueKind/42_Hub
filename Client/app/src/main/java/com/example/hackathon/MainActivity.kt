package com.example.hackathon

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Display
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.find

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var qrEncoder: QRGEncoder

    lateinit var qrIV: ImageView
    lateinit var bitmap: Bitmap

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

        fun generateQR(Name: String, Email: String, image: ImageView){
            // on below line we are getting service for window manager
            val windowManager: WindowManager = getSystemService(WINDOW_SERVICE) as WindowManager

            // on below line we are initializing a
            // variable for our default display
            val display: Display = windowManager.defaultDisplay

            // on below line we are creating a variable
            // for point which is use to display in qr code
            val point: Point = Point()
            display.getSize(point)

            // on below line we are getting
            // height and width of our point
            val width = point.x
            val height = point.y

            // on below line we are generating
            // dimensions for width and height
            var dimen = if (width < height) width else height
            dimen = dimen * 3 / 4

            // on below line we are initializing our qr encoder
            qrEncoder = QRGEncoder("$Name#$Email#$image", null, QRGContents.Type.TEXT, dimen)

            // on below line we are running a try
            // and catch block for initialing our bitmap
            try {
                // on below line we are
                // initializing our bitmap
                bitmap = qrEncoder.encodeAsBitmap()

                // on below line we are setting
                // this bitmap to our image view
                qrIV.setImageBitmap(bitmap)
            } catch (e: Exception) {
                // on below line we
                // are handling exception
                e.printStackTrace()
            }
        }

        val ttext1 = findViewById<TextView>(R.id.textView3)
        val ttext2 = findViewById<TextView>(R.id.textView4)
        val image1 = findViewById<ImageView>(R.id.imageView2)
        val extras = intent.extras
        if (extras != null) {
            var value = extras.getString("soso").toString()
            Toast.makeText(this,value, Toast.LENGTH_LONG).show()
            GetInfo().reqUserInfo(ttext1,ttext2,image1,value)
            generateQR(ttext1.toString(),ttext2.toString(),image1)
            //The key argument here must match that used in the other activity
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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var soso1 = findViewById<CircleImageView>(R.id.soso)
        soso1.setOnClickListener {
            drawerLayout.openDrawer(navView)
        }


    }
}