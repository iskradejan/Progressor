package com.progressor.progressor

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.progressor.progressor.Model.Model
import com.progressor.progressor.Services.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var apiUtil: ApiUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerApiComponent.create().inject(this)
//        ApiComponent.create().poke(this)

        mainHeader.text = "DEJAN IS THE BEST"

        mainButton.setOnClickListener {
            //            val testPojo = TestPojo("dejan", "iskra")
//            val intent = Intent(this, SplashActivity::class.java)
////            intent.putExtra(SplashActivity.SPLASH_HEADER, "GLORY GLORY!")
//            intent.putExtra("Object", testPojo)
//            startActivity(intent)

            apiUtil.getHitCount().subscribe(
                    { response -> successProcessor(response) },
                    { error -> errorProcessor(error) }
            )
        }
    }

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
