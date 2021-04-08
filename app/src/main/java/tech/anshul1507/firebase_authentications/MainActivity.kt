package tech.anshul1507.firebase_authentications

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import tech.anshul1507.firebase_authentications.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        initApp()
    }

    private fun initApp() {
        mAuth = Firebase.auth

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

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}