package com.singhvikrant.mobilecomputingtasks.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.singhvikrant.mobilecomputingtasks.R

class SignupActivity : AppCompatActivity() {

    var inputUser:EditText = findViewById(R.id.userName)
    var inputEmail:EditText = findViewById(R.id.userEmail)
    var inputpass:EditText = findViewById(R.id.userPass)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        val showPass: ImageView = findViewById(R.id.show_pass_btn)
        val hidePass: ImageView = findViewById(R.id.hide_pass_btn)
        val password: EditText = findViewById(R.id.userPass)
        val signIn:TextView = findViewById(R.id.signIn)

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


        signIn.setOnClickListener {
            val message = ""
            val intent = Intent(this, LoginActivity::class.java).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, message)
            }
            startActivity(intent)
            finish()
        }





        /////////////////////////////////////////////// Firebase Auth ///////////////////////////////////////////////






    }
}