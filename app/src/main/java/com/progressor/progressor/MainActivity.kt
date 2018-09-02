package com.progressor.progressor

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.progressor.progressor.components.DaggerUtilComponent
import com.progressor.progressor.dataobjects.account.*
import com.progressor.progressor.modules.ApiModule
import java.time.LocalDateTime
import java.time.Month
import javax.inject.Inject
import com.progressor.progressor.components.MainComponentInterface
import com.progressor.progressor.components.UtilComponent
import com.progressor.progressor.modules.FragmentModule
import com.progressor.progressor.services.FragmentNavigator
import com.progressor.progressor.views.fragment.LoginFragment
import com.progressor.progressor.views.fragment.SplashFragment

class MainActivity : AppCompatActivity(), MainComponentInterface {
    private var utilComponent: UtilComponent? = null

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    @Inject
    lateinit var fragmentNavigator: FragmentNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)
        injectDependencies()

        println("THIS IS HOTFIXXXXX")
        if (sharedPreferences.getBoolean("firstTime", true)) {
            println("FIRST TIME, SENDING TO SPLASH FRAGMENT")
            println("Normal work goes on")
            fragmentNavigator.navigate(SplashFragment())
        } else {
            fragmentNavigator.navigate(LoginFragment())
        }
    }

    override fun getMainComponent(): UtilComponent? {
        utilComponent?.let { return it }

        utilComponent = DaggerUtilComponent
                .builder()
                .apiModule(ApiModule(this))
                .fragmentModule(FragmentModule(this))
                .build()

        return utilComponent
    }

    private fun injectDependencies() {
        mainComponent?.inject(this)
    }

    // TODO: this is how you create global object/value
    companion object Singleton {
        init {
            println("Faked User Object")
        }

        private val workout = Workout(1, "Burn Baby Burn", LocalDateTime.now())
        private val body = Body(180, 30, 40, 30, LocalDateTime.now())
        private val workoutList: MutableList<Workout> = arrayListOf<Workout>(workout)
        private val bodyHistoryList: MutableList<Body> = arrayListOf<Body>(body)

        var user = User(
                Login("diskra70@gmail.com"),
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
