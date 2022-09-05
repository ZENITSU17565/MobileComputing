package com.singhvikrant.mobilecomputingtasks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val showPass: ImageView = findViewById(R.id.show_pass_btn)
        val hidePass: ImageView = findViewById(R.id.hide_pass_btn)
        val password:EditText = findViewById(R.id.userPass)
        val skip:TextView = findViewById(R.id.skip)
        val createAccount:TextView = findViewById(R.id.signUp)

        showPass.setOnClickListener {
            showPass.visibility = View.GONE
            hidePass.visibility = View.VISIBLE
            password.transformationMethod = HideReturnsTransformationMethod.getInstance()

        }
        hidePass.setOnClickListener {
            showPass.visibility = View.VISIBLE
            hidePass.visibility = View.GONE
            password.transformationMethod = PasswordTransformationMethod.getInstance()
        }

        skip.setOnClickListener {
            val message = ""
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, message)
            }
            startActivity(intent)
            finish()
        }

        createAccount.setOnClickListener {
            val message = ""
            val intent = Intent(this, SignupActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, message)
            }
            startActivity(intent)
            finish()
        }
    }
}