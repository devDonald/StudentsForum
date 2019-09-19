package com.donald.studentsforum

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var tv_new_user = findViewById<View>(R.id.tv_not_registered) as TextView

        var tv_forgot_password: TextView = findViewById(R.id.tv_forgot_password)

        val et_login_email: EditText = findViewById(R.id.et_login_email)

        val et_login_password: EditText = findViewById(R.id.et_login_password)

        val bt_submit_login: Button = findViewById(R.id.bt_login_submit)


        val sharedPref =  getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)

        val savedEmail = sharedPref.getString("email","")
        val savePassword = sharedPref.getString("password","")

        if (savedEmail!="" && savePassword!=""){

            val dashboard = Intent(this,MainActivity:: class.java)
            startActivity(dashboard)
            finish()
        }

        bt_submit_login.setOnClickListener(View.OnClickListener {
            val email:String = et_login_email.text.toString().trim()
            val password :String = et_login_password.text.toString().trim()


            if (email.isEmpty()){
                et_login_email.setError("Email is Empty")
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                et_login_email.setError("invalid email")
            } else if(password.isEmpty()){
                et_login_password.setError("password empty")
            } else if (password.length<8){
                et_login_password.setError("password too short")
            } else {


                val sharedPreference =  getSharedPreferences("LoginDetails",Context.MODE_PRIVATE)

                val editor = sharedPreference.edit()

                editor.putString("email",email)
                editor.putString("password",password)

                editor.apply()


                val dashboard = Intent(this,MainActivity:: class.java)
                startActivity(dashboard)
                finish()
                Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()


            }

        })

        tv_new_user.setOnClickListener(View.OnClickListener {

            var signup = Intent(this, SignUp::class.java)

            startActivity(signup)

        })

        tv_forgot_password.setOnClickListener(View.OnClickListener {
            var forgot = Intent(this, ForgotPassword::class.java)

            startActivity(forgot)
        })
    }




}
