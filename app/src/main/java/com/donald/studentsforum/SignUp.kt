package com.donald.studentsforum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.donald.studentsforum.helpers.DatabaseHelper
import com.donald.studentsforum.model.Users

class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val et_name:EditText = findViewById(R.id.et_signup_name)
        val et_email:EditText = findViewById(R.id.et_signup_email)
        val et_password:EditText = findViewById(R.id.et_signup_password)
        val et_cpassword:EditText = findViewById(R.id.et_signup_cpassword)
        val et_address:EditText = findViewById(R.id.et_signup_address)
        val bt_submit: Button = findViewById(R.id.bt_signup_submit)


        bt_submit.setOnClickListener(View.OnClickListener {
            val name:String = et_name.text.toString().trim()
            val email:String = et_email.text.toString().trim()
            val password: String = et_password.text.toString().trim()
            val address:String = et_address.text.toString().trim()
            val cPassword:String = et_cpassword.text.toString()

            if (name.isEmpty()){
                et_name.setError("Empty field")
            } else if(email.isEmpty()){
                et_email.setError("Empty field")

            } else if (!email.contains("@")|| !email.contains(".")){
                et_email.setError("invalid email")

            } else if (password.isEmpty() || cPassword.isEmpty()){
                et_password.setError("Empty field")

            } else if (password != cPassword){
                et_cpassword.setError("password mismatch")

            } else if(address.isEmpty()){
                et_address.setError("Empty field")

            } else{

                //create the Database helper instance to push your form data to

                val databaseHelper= DatabaseHelper(this)

                if (!databaseHelper.checkUser(email)){
                   val user= Users(name = name, email = email, password = password,
                       address = address)
                    databaseHelper.addUsers(user)

                    Toast.makeText(this,"Sign up Successful", Toast.LENGTH_LONG).show()
                    val toLogin = Intent(this,LoginActivity::class.java)
                    startActivity(toLogin)
                    finish()


                } else{
                    Toast.makeText(this,"User already exist", Toast.LENGTH_LONG).show()

                }

            }
        })

    }
}
