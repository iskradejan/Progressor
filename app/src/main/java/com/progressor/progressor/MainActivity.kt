package com.progressor.progressor

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.progressor.progressor.components.DaggerApiComponent
import com.progressor.progressor.dataobjects.Dejan
import com.progressor.progressor.dataobjects.Model
import com.progressor.progressor.services.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var apiUtil: ApiUtil
    @Inject
    lateinit var hitFetcher: HitFetcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerApiComponent.create().inject(this)

        // TODO: Experimenting with Singleton
        println("weight 1 ..... " + MainActivity.name.weight)
        val dejan = Dejan(100)
        MainActivity.name = dejan
        println("weight 2 ..... " + MainActivity.name.weight)
        // end

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
            println("Singleton class invoked.")
        }

        var name = Dejan(188)
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
