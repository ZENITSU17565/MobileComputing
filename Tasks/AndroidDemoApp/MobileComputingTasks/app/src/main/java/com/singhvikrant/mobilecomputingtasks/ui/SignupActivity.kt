package com.singhvikrant.mobilecomputingtasks.ui

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.singhvikrant.mobilecomputingtasks.R

class SignupActivity : AppCompatActivity() {

    lateinit var inputUser:EditText
    lateinit var inputEmail:EditText
    lateinit var inputpass:EditText

    lateinit var register:TextView

    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    lateinit var progressDialog:ProgressDialog


    private lateinit var mAuth:FirebaseAuth
    private lateinit var mUser:FirebaseUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        val showPass: ImageView = findViewById(R.id.show_pass_btn)
        val hidePass: ImageView = findViewById(R.id.hide_pass_btn)
        val password: EditText = findViewById(R.id.userPass)
        val signIn:TextView = findViewById(R.id.signIn)


        mAuth = FirebaseAuth.getInstance()
        mAuth.currentUser.also {
            if (it != null) {
                mUser = it
            }
        }

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



        inputUser = findViewById(R.id.userName)
        inputEmail = findViewById(R.id.userEmail)
        inputpass = findViewById(R.id.userPass)
        register = findViewById(R.id.signUp)
        progressDialog = ProgressDialog(this)

        register.setOnClickListener {
            Log.i("***********","onClick")

            PerformAuth()
        }

    }

    private fun PerformAuth() {
        var userName = inputUser.text.toString()
        var emailId = inputEmail.text.toString()
        var passward = inputpass.text.toString()
        Log.i("***********","$emailId")

        if (!emailId.matches(emailPattern.toRegex())){
            inputEmail.setError("Enter valid email")
        }
        else if (passward.isEmpty() || passward.length < 7){
            inputpass.setError("Enter proper password")
        }
        else{
            progressDialog.setMessage("Please wait while Registration...")
            progressDialog.setTitle("Registration")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            mAuth.createUserWithEmailAndPassword(emailId, passward).addOnCompleteListener {
                if (it.isSuccessful){
                    progressDialog.dismiss()
                    sendUserToNextActivity()
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT)
                }else{
                    progressDialog.dismiss()
                    Toast.makeText(this, ""+it.exception, Toast.LENGTH_SHORT)
                }
            }


        }

    }

    private fun sendUserToNextActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}