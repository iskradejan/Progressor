package com.progressor.progressor

import activities.DashboardActivity
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
import activities.SplashActivity

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseUser: FirebaseUser? = null

    override fun onBackPressed() {
        Toast.makeText(baseContext, "Nice try... you are stuck forever!", Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerApiComponent.builder().apiModule(ApiModule(this)).build().inject(this)
        firebaseAuth = FirebaseAuth.getInstance();

        if(sharedPreferences.getBoolean("firstTime", true)) {
            val splashIntent = Intent(this, SplashActivity::class.java)
            startActivity(splashIntent)
        } else {
            println("IS NOT firstTime")
        }

        mainSignIn.setOnClickListener {
            signIn(mainEmail.getText().toString(), mainPassword.getText().toString());
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        firebaseUser = firebaseAuth?.getCurrentUser()
//        updateUI(currentUser)
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = mainEmail.getText().toString()
        if (TextUtils.isEmpty(email)) {
            mainEmail.setError("Required.")
            valid = false
        } else {
            mainEmail.setError(null)
        }

        val password = mainPassword.getText().toString()
        if (TextUtils.isEmpty(password)) {
            mainPassword.setError("Required.")
            valid = false
        } else {
            mainPassword.setError(null)
        }

        return valid
    }

    private fun signIn(email: String, password: String) {
        println("signIn:$email")
        if (!validateForm()) {
            return
        }

        firebaseAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        firebaseUser = firebaseAuth?.getCurrentUser()
                        MainActivity.user.login?.email = firebaseUser?.email
                        val dashboardIntent = Intent(this, DashboardActivity::class.java)
                        startActivity(dashboardIntent)
                    } else {
                        println("Authentication failed:\n" + task.exception)
                        Toast.makeText(baseContext, "Authentication failed...", Toast.LENGTH_LONG).show()
                    }
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
                Login("xxx"),
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
