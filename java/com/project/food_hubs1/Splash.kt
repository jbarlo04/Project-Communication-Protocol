package com.project.food_hubs1

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.project.food_hubs1.util.GlobalVariable
import com.project.food_hubs1.util.SharedHelper


class Splash : AppCompatActivity() {



    lateinit var sharedStorage: SharedHelper

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


         sharedStorage = SharedHelper()


      starthandler()


    }





    fun starthandler()
    {


        val handler = Handler()
        handler.postDelayed({
            if (sharedStorage.getKey(this@Splash,GlobalVariable.variables.UserType).isEmpty()) {

                startActivity(Intent(this@Splash, MainActivity::class.java))

                finish()


            } else if (sharedStorage.getKey(this@Splash,GlobalVariable.variables.UserType).equals("user")) {
                intent  = Intent(this@Splash, UserDash::class.java)
                intent.putExtra("usermobile", sharedStorage.getKey(this@Splash,GlobalVariable.variables.mobile))
                intent.putExtra("city",sharedStorage.getKey(this@Splash,"city"))
                intent.putExtra("username", sharedStorage.getKey(this@Splash,GlobalVariable.variables.uname))
                startActivity(intent)

                finish()
            } else{
                val intent = Intent(applicationContext, CaterDash::class.java)
                intent.putExtra("usermobile", sharedStorage.getKey(this@Splash,GlobalVariable.variables.mobile))
                intent.putExtra("username", sharedStorage.getKey(this@Splash,GlobalVariable.variables.uname))
                intent.putExtra("userbname", sharedStorage.getKey(this@Splash,GlobalVariable.variables.bname))
                startActivity(intent)
                finish()
            }
        }, 3000)
    }






}
