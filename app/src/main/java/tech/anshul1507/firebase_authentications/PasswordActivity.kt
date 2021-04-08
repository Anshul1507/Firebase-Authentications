package tech.anshul1507.firebase_authentications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tech.anshul1507.firebase_authentications.databinding.ActivityMainBinding
import tech.anshul1507.firebase_authentications.databinding.ActivityPasswordBinding

class PasswordActivity : AppCompatActivity() {

    private val viewBinding: ActivityPasswordBinding by lazy {
        ActivityPasswordBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }
}