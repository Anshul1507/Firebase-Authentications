package tech.anshul1507.firebase_authentications

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import tech.anshul1507.firebase_authentications.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private val viewBinding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        initApp()
    }

    private fun initApp() {
        mAuth = Firebase.auth
        val mUser = mAuth.currentUser

        viewBinding.homeUserName.text = "User name: ${mUser?.displayName}"
        viewBinding.homeEmail.text = "E-mail: ${mUser?.email}"

        onClickListeners()
    }

    private fun onClickListeners() {
        /**
         *  Log out button
         * */
        viewBinding.homeLogoutBtn.setOnClickListener {
            showAlertDialog(
                "Logout",
                "Are you sure to Log out?",
                "Yes",
                { dialog, _ ->
                    dialog.dismiss()
                    signOut()
                },
                "Cancel",
                { dialog, _ ->
                    dialog.dismiss()
                },
                false
            )

        }
    }

    private fun signOut() {
        Firebase.auth.signOut()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showAlertDialog(
        title: String,
        msg: String,
        positiveLabel: String,
        positiveOnClick: DialogInterface.OnClickListener,
        negativeLabel: String,
        negativeOnClick: DialogInterface.OnClickListener,
        isCancelable: Boolean
    ): AlertDialog {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setPositiveButton(positiveLabel, positiveOnClick)
        builder.setNegativeButton(negativeLabel, negativeOnClick)
        builder.setCancelable(isCancelable)

        val alert = builder.create()
        alert.show()
        return alert
    }
}