package com.progressor.progressor

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.progressor.progressor.Model.TestPojo
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    companion object {
        const val SPLASH_HEADER = "--"
        const val SPLASH_SUB_HEADER = "--"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val testPojo = intent.getSerializableExtra("Object") as TestPojo
        splashHeader.text = testPojo.firstName
        splashSubHeader.text = testPojo.lastName

//        splashHeader.text = intent.getStringExtra(SPLASH_HEADER)
//        splashSubHeader.text = intent.getStringExtra(SPLASH_SUB_HEADER)
    }
}
