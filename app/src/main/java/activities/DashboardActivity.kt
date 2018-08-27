package activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.progressor.progressor.MainActivity
import com.progressor.progressor.R
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        dashboardUserEmail.text = MainActivity.user.login?.email
    }
}