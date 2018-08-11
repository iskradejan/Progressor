package com.progressor.progressor

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.progressor.progressor.Model.Model
import com.progressor.progressor.Services.SOServiceInterface
import kotlinx.android.synthetic.main.activity_main.*
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.*

class MainActivity : AppCompatActivity() {
    private var disposable: Disposable? = null

    private val sOServiceInterface by lazy {
        SOServiceInterface.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainHeader.text = "DEJAN IS THE BEST"

        mainButton.setOnClickListener {
            //            val testPojo = TestPojo("dejan", "iskra")
//            val intent = Intent(this, SplashActivity::class.java)
////            intent.putExtra(SplashActivity.SPLASH_HEADER, "GLORY GLORY!")
//            intent.putExtra("Object", testPojo)
//            startActivity(intent)

            test()
        }
    }

    private fun successProcessor(response: Model.Result) {
        this.mainHeader.text = String.format(Locale.US, Integer.toString(response.query.searchinfo.totalhits))
        println(Integer.toString(response.query.searchinfo.totalhits))
    }

    private fun errorProcessor(error: Throwable?) {
        println("ERROR: ")
        println(error?.localizedMessage)
        println(error?.message)
    }

    private fun test(){
        sOServiceInterface.hitCountCheck("query", "json", "search", "pogba")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> successProcessor(result) },
                        { error -> errorProcessor(error) }
                )
    }
}
