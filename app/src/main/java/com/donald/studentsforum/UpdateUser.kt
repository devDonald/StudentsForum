package com.donald.studentsforum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.donald.studentsforum.helpers.DatabaseHelper
import com.donald.studentsforum.model.Users

class UpdateUser : AppCompatActivity() {
    private lateinit var et_name: EditText
    private lateinit var et_email: EditText
    private lateinit var et_address: EditText
    private lateinit var et_password: EditText

    //create global string variables
    private lateinit var update_name:String
    private lateinit var update_email:String
    private lateinit var update_address:String
    private lateinit var update_password:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        val id = intent.getIntExtra("id",-1)
        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val address = intent.getStringExtra("address")
        val password = intent.getStringExtra("password")


        et_name = findViewById(R.id.update_user_name)
        et_email = findViewById(R.id.update_user_email)
        et_address = findViewById(R.id.update_user_address)
        et_password = findViewById(R.id.update_user_password)

        et_name.setText(name)
        et_email.setText(email)
        et_address.setText(address)
        et_password.setText(password)

        val update_bt: Button = findViewById(R.id.update_user_update)

        update_bt.setOnClickListener(View.OnClickListener {

            //get updated values from the update user layout
            update_name = et_name.text.toString().trim()
            update_email = et_email.text.toString().trim()
            update_address = et_address.text.toString().trim()
            update_password = et_password.text.toString().trim()


            //create the database helper instance
            val db_helper = DatabaseHelper(this)

            //insert the updated values to the Users class object
            val users = Users(id = id,name = update_name,email = update_email, password = update_password,address = update_address)

            //call the update user function of the Database helper to update the user's data
            db_helper.updateUser(users)

            //redirect the user to MainActivity
            val toUpdate = Intent(this, MainActivity::class.java)

            toUpdate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            toUpdate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            toUpdate.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(toUpdate)
            finish()

        })
    }
}
