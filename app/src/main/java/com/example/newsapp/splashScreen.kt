package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*

class splashScreen : AppCompatActivity (){
    lateinit var handler : Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

// set it as main activity i.e opening activity in manifest file
        handler = Handler()
        handler.postDelayed({
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()  // finish this activity here , so that it doesn't remain in stack
        } ,  3500 )  //delaying 3.5 seconds to open main activity



        setContentView(R.layout.activity_splash_screen)

        newsFlash.animate().apply {
            duration = 1300
            rotationYBy(360f)
        }.withEndAction {
            newsFlash.animate().apply {
                duration = 1300
                rotationXBy(360f)
            }
        }.start()
    }

}