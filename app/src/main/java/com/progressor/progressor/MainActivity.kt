package com.progressor.progressor

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.progressor.progressor.components.DaggerApiComponent
import com.progressor.progressor.dataobjects.Model
import com.progressor.progressor.dataobjects.account.*
import com.progressor.progressor.services.*
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.Month
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var apiUtil: ApiUtil
    @Inject
    lateinit var hitFetcher: HitFetcher
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerApiComponent.create().inject(this)

        if(sharedPreferences.getBoolean("firstTime", false)) {
            println("NOT firstTime")
        } else {
            sharedPreferences.edit().putBoolean("firstTime", true).apply()
            println("IS firstTime")
        }

        mainHeader.text = "DEJAN IS THE BEST"

        mainButton.setOnClickListener {
            val intent = Intent(this, SplashActivity::class.java)
            intent.putExtra(SplashActivity.SPLASH_HEADER, "GLORY GLORY!")
            startActivity(intent)

            // individual service
//            hitFetcher.fetch().subscribe(
//                    { response -> successProcessor(response) },
//                    { error -> errorProcessor(error) }
//            )
            // standard api util
//            apiUtil.getHitCount().subscribe(
//                    { response -> successProcessor(response) },
//                    { error -> errorProcessor(error) }
//            )
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

    private fun successProcessor(response: Model.Result) {
        this.mainHeader.text = String.format(Locale.US, Integer.toString(response.query.searchinfo.totalhits))
        println("IT WORKS: " + Integer.toString(response.query.searchinfo.totalhits))
    }

    private fun errorProcessor(error: Throwable?) {
        println("ERROR: ")
        println(error?.localizedMessage)
        println(error?.message)
    }

}
