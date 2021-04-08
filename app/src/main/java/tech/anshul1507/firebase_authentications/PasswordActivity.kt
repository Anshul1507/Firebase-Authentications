package tech.anshul1507.firebase_authentications

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import tech.anshul1507.firebase_authentications.databinding.ActivityPasswordBinding

class PasswordActivity : AppCompatActivity() {

    private val viewBinding: ActivityPasswordBinding by lazy {
        ActivityPasswordBinding.inflate(layoutInflater)
    }

    private var flagPattern: Int = 1
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        initToolBar()
        initApp()
    }

    private fun initToolBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initApp() {
        // Hide name et for login pattern
//        viewBinding.nameEt.visibility = View.INVISIBLE

        mAuth = Firebase.auth
        onClickListeners()
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            //updateUI()
        }
    }

    private fun onClickListeners() {

        /**
         *  The only button click listener based on patterns
         * */
        viewBinding.emailBtn.setOnClickListener {
            handleEmailBtnClickListener()
        }

        /**
         *  Sign up text click listener
         * */
        viewBinding.signUpEmailTv.setOnClickListener {
            handleSignUpTextClickListener()
        }

        /**
         *  Reset Password text click listener
         * */
        viewBinding.forgotPasswordEmailTv.setOnClickListener {
            handleResetPasswordTextClickListener()
        }
    }

    private fun handleEmailBtnClickListener() {
        /**
         * Keep the default to login pattern
         * 0 => Login
         * 1 => Sign up
         * 2 => Reset
         */
        when (flagPattern) {
            0 -> {
                emailLoginMethod()
            }
            1 -> {
                emailSignUpMethod()
            }
            else -> {
                emailResetMethod()
            }
        }
    }

    private fun emailLoginMethod() {
        //TODO:: Login Using Email and Password method
    }

    private fun emailSignUpMethod() {
        // Getting Run-time name, email and password
        val name = viewBinding.nameEt.text.toString()
        val mail = viewBinding.emailEt.text.toString()
        val password = viewBinding.passwordEt.text.toString()

        mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                logger("sign up email success")

                updateUserInfo(name) // Updating Profile of just signed user.
            } else {
                // If sign in fails, display a message to the user.
                logger("sign up email failed")
                Toast.makeText(
                    baseContext, "Authentication failed. Retry",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateUserInfo(name: String) {
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))  /* Here you can update the image of user */
            .build()

        mAuth.currentUser.updateProfile(profileUpdates)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    logger("sign up name update success")
                    //TODO::updateUI()
                } else {
                    // If sign in fails, display a message to the user.
                    logger("sign up name update failed")
                    Toast.makeText(
                        baseContext, "Authentication failed. Retry",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun emailResetMethod() {
        //TODO:: Reset Password method
    }

    private fun handleSignUpTextClickListener() {
        //TODO:: Change views based on sign up pattern
    }

    private fun handleResetPasswordTextClickListener() {
        //TODO:: Change views based on reset password pattern
    }

    private fun logger(s: String) {
        Log.d("Logs: ", "$s")
    }
}