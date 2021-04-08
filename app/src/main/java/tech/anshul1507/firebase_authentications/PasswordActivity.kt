package tech.anshul1507.firebase_authentications

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import tech.anshul1507.firebase_authentications.databinding.ActivityMainBinding
import tech.anshul1507.firebase_authentications.databinding.ActivityPasswordBinding

class PasswordActivity : AppCompatActivity() {

    private val viewBinding: ActivityPasswordBinding by lazy {
        ActivityPasswordBinding.inflate(layoutInflater)
    }

    private var flagPattern: Int = 0

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
        viewBinding.signUpEmailTv.setOnClickListener {
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
        //TODO:: Sign up method
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
}