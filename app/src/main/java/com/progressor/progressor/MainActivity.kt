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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.text.TextUtils
import kotlinx.android.synthetic.main.activity_main.*
import com.progressor.progressor.views.activity.SplashActivity
import com.progressor.progressor.views.activity.presenter.MainActivityPresenter

class MainActivity : AppCompatActivity(), MainActivityPresenter.View {

    @Inject lateinit var presenter: MainActivityPresenter
    @Inject lateinit var sharedPreferences: SharedPreferences

    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerApiComponent.builder().apiModule(ApiModule(this)).build().inject(this)
        presenter.setPresenter(this)

        firebaseAuth = FirebaseAuth.getInstance();

        if(sharedPreferences.getBoolean("firstTime", true)) {
            val splashIntent = Intent(this, SplashActivity::class.java)
            startActivity(splashIntent)
        }

        mainSignIn.setOnClickListener {
            presenter.login(mainEmail.text.toString(), mainPassword.text.toString())
        }
    }

    public override fun onStart() {
        super.onStart()
        firebaseUser = firebaseAuth?.getCurrentUser()
    }

    override fun onBackPressed() {
        Toast.makeText(baseContext, "Nice try... you are stuck forever!", Toast.LENGTH_LONG).show()
    }

    override fun getFirebaseAuth(): FirebaseAuth? {
        return firebaseAuth
    }

    override fun isFormValid(): Boolean {
        var valid = true

        if (TextUtils.isEmpty(mainEmail.getText().toString())) {
            mainEmail.setError("Required.")
            valid = false
        } else {
            mainEmail.setError(null)
        }

        if (TextUtils.isEmpty(mainPassword.getText().toString())) {
            mainPassword.setError("Required.")
            valid = false
        } else {
            mainPassword.setError(null)
        }

        return valid
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
