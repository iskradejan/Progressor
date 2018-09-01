package com.progressor.progressor.views.activity

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.progressor.progressor.MainActivity
import com.progressor.progressor.R
import com.progressor.progressor.components.DaggerComponent
import com.progressor.progressor.modules.ApiModule
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onBackPressed() {
        Toast.makeText(baseContext, "Nice try... now login!", Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        DaggerComponent.builder().apiModule(ApiModule(this)).build().inject(this)

        splashHeader.text = MainActivity.user.person?.firstName ?: "none"
        splashSubHeader.text = MainActivity.user.person?.lastName ?: "none"

        sharedPreferences.edit().putBoolean("firstTime", false).apply()

        splashLogin.setOnClickListener {
            val loginIntent = Intent(this, MainActivity::class.java)
            startActivity(loginIntent)
        }
    }
}
