package tech.anshul1507.firebase_authentications

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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

    private var flagPattern: Int = 0
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
        viewBinding.nameEt.visibility = View.INVISIBLE

        mAuth = Firebase.auth
        onClickListeners()
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
        // Global Check for mail, not to be null (Primary Key in all 3 cases)
        if (viewBinding.emailEt.text.isNullOrEmpty()) {
            Toast.makeText(this, "Please type your email", Toast.LENGTH_SHORT).show()
            return
        }
        when (flagPattern) {
            0 -> {
                if (viewBinding.passwordEt.text.isNotEmpty()) {
                    emailLoginMethod()
                } else {
                    Toast.makeText(this, "Please type your password", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            1 -> {
                if (viewBinding.nameEt.text.isNotEmpty() && viewBinding.passwordEt.text.isNotEmpty()) {
                    emailSignUpMethod()
                } else {
                    when (viewBinding.nameEt.text.isNullOrEmpty()) {
                        true -> {
                            Toast.makeText(this, "Please type your name", Toast.LENGTH_SHORT)
                                .show()
                        }
                        false -> {
                            Toast.makeText(this, "Please type your password", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
            else -> {
                emailResetMethod()
            }
        }
    }

    /**
     * Login/Sign up/Reset Methods
     */
    private fun emailLoginMethod() {
        val mail = viewBinding.emailEt.text.toString()
        val password = viewBinding.passwordEt.text.toString()

        mAuth.signInWithEmailAndPassword(mail, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    logger("sign in email success")
                    updateUI()
                } else {
                    // If sign in fails, display a message to the user.
                    // TODO:: More clear error message in toast to user or regex to check email
                    logger("sign in email failure")
                    Toast.makeText(
                        baseContext, "Authentication failed. Retry",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
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
                    updateUI()
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
        val mail = viewBinding.emailEt.text.toString()

        Firebase.auth.sendPasswordResetEmail(mail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    logger("email reset mail success")
                    Toast.makeText(
                        baseContext, "Reset Password Link sent to your mail",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    logger("email reset mail failure")
                    Toast.makeText(
                        baseContext, "Failure. Retry",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    /**
     * UI Checks for all patterns
     */
    private fun handleSignUpTextClickListener() {
        when (flagPattern) {
            0 -> {
                //On sign up screen, text view contains the info of sign in
                viewBinding.signUpEmailTv.text = "Signed up already? Click to Login"
                viewBinding.nameEt.visibility = View.VISIBLE
                viewBinding.emailBtn.text = "Sign up"
                flagPattern = 1 //to handle button as sign up button
            }
            else -> {
                //On sign in, text view contains the info of sign up
                viewBinding.signUpEmailTv.text = "Hadn't Signed in with yet? Click to sign up"
                viewBinding.nameEt.visibility = View.INVISIBLE
                viewBinding.emailBtn.text = "Login"
                flagPattern = 0
            }
        }
    }

    private fun handleResetPasswordTextClickListener() {
        viewBinding.signUpEmailTv.visibility = View.GONE
        viewBinding.nameEt.visibility = View.INVISIBLE
        viewBinding.passwordEt.visibility = View.GONE
        viewBinding.emailBtn.text = "Reset Password"
        flagPattern = 2
    }

    /**
     * Update UI function
     */
    private fun updateUI() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK
        )
        startActivity(intent)
    }

    private fun logger(s: String) {
        Log.d("Logs: ", "$s")
    }
}