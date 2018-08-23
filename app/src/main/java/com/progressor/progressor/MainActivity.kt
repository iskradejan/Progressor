package com.progressor.progressor

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.progressor.progressor.components.DaggerApiComponent
import com.progressor.progressor.dataobjects.account.*
import com.progressor.progressor.modules.ApiModule
import java.time.LocalDateTime
import java.time.Month
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onBackPressed() {
        Toast.makeText(baseContext, "Nice try... you are stuck forever!", Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerApiComponent.builder().apiModule(ApiModule(this)).build().inject(this)

        if(sharedPreferences.getBoolean("firstTime", true)) {
            val splashIntent = Intent(this, SplashActivity::class.java)
            startActivity(splashIntent)
        } else {
            println("IS NOT firstTime")
        }
    }

    // TODO: this is how you create global object/value
    companion object Singleton {
        init {
            println("Faked User Object")
        }

        private val workout = Workout(1,"Burn Baby Burn", LocalDateTime.now())
        private val body = Body(180,30, 40, 30, LocalDateTime.now())
        private val workoutList: MutableList<Workout> = arrayListOf<Workout>(workout)
        private val bodyHistoryList: MutableList<Body> = arrayListOf<Body>(body)

        var user = User(
                Person("Dejan", "Iskra", 1, LocalDateTime.of(1990, Month.DECEMBER, 16, 10, 10, 30), 72, 1, LocalDateTime.now()),
                Profile(
                        workouts = workoutList,
                        bodyHistory = bodyHistoryList
                ),
                true
                )
    }
    // end
}
