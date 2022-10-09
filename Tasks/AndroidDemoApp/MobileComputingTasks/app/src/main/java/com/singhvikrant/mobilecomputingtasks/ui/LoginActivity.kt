package com.singhvikrant.mobilecomputingtasks.ui

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
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

class LoginActivity : AppCompatActivity() {

//    lateinit var inputUser:EditText
    lateinit var inputEmail:EditText
    lateinit var inputpass:EditText

    lateinit var login:TextView

    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    lateinit var progressDialog: ProgressDialog


    private lateinit var mAuth: FirebaseAuth
    private lateinit var mUser: FirebaseUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        mAuth.currentUser.also {
            if (it != null) {
                mUser = it
            }
        }



        inputEmail = findViewById(R.id.userName)
        login = findViewById(R.id.signIn)
        progressDialog = ProgressDialog(this)


        val showPass: ImageView = findViewById(R.id.show_pass_btn)
        val hidePass: ImageView = findViewById(R.id.hide_pass_btn)
        inputpass = findViewById(R.id.userPass)
        val skip:TextView = findViewById(R.id.skip)
        val createAccount:TextView = findViewById(R.id.signUp)

        showPass.setOnClickListener {
            showPass.visibility = View.GONE
            hidePass.visibility = View.VISIBLE
            inputpass.transformationMethod = HideReturnsTransformationMethod.getInstance()

        }
        hidePass.setOnClickListener {
            showPass.visibility = View.VISIBLE
            hidePass.visibility = View.GONE
            inputpass.transformationMethod = PasswordTransformationMethod.getInstance()
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


        login.setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin() {
//        var userName = inputUser.text.toString()
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
            progressDialog.setMessage("Please wait while Login...")
            progressDialog.setTitle("Login")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            mAuth.signInWithEmailAndPassword(emailId, passward).addOnCompleteListener {
                if(it.isSuccessful){
                    progressDialog.dismiss()
                    sendUserToNextActivity()
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT)
                }else{
                    progressDialog.dismiss()
                    Toast.makeText(this, ""+it.exception, Toast.LENGTH_SHORT)
                }
            }
        }
    }

    private fun sendUserToNextActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}