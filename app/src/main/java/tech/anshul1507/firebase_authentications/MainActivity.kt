package tech.anshul1507.firebase_authentications

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tech.anshul1507.firebase_authentications.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        onClickListeners()
    }

    private fun onClickListeners() {

        /**
         *  E-Mail Sign in Button Click Listener
         *  => Opens up PasswordActivity.kt on Click
         * */
        viewBinding.emailSignIn.setOnClickListener {
            startActivity(Intent(this, PasswordActivity::class.java))
        }

    }
}