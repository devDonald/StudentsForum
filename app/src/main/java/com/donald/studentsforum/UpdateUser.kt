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
    //TODO 2: Add the following codes to your Update User Activity
    private lateinit var et_name: EditText
    private lateinit var et_email: EditText
    private lateinit var et_address: EditText
    private lateinit var et_password: EditText

    //TODO 3: create global string variables
    private lateinit var update_name:String
    private lateinit var update_email:String
    private lateinit var update_address:String
    private lateinit var update_password:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        //TODO 4: rettieve the values you sent from View User Activity
        val id = intent.getIntExtra("id",-1)
        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val address = intent.getStringExtra("address")
        val password = intent.getStringExtra("password")


        //TODO 5: Link the global variables to their respective view from your xml files
        et_name = findViewById(R.id.update_user_name)
        et_email = findViewById(R.id.update_user_email)
        et_address = findViewById(R.id.update_user_address)
        et_password = findViewById(R.id.update_user_password)

        //TODO 6: set text to your EditTexts
        et_name.setText(name)
        et_email.setText(email)
        et_address.setText(address)
        et_password.setText(password)

        //TODO 7: Create a variable for the button that will update your views
        val update_bt: Button = findViewById(R.id.update_user_update)

        update_bt.setOnClickListener(View.OnClickListener {

            // TODO 8: get updated values that the user changed from the update user layout
            update_name = et_name.text.toString().trim()
            update_email = et_email.text.toString().trim()
            update_address = et_address.text.toString().trim()
            update_password = et_password.text.toString().trim()


            //TODO 9: create the database helper instance
            val db_helper = DatabaseHelper(this)

            //TODO 10: insert the updated values to the Users class object
            val users = Users(id = id,name = update_name,email = update_email, password = update_password,address = update_address)

            //TODO 11: call the update user function of the Database helper to update the user's data
            db_helper.updateUser(users)

            //TODO 12: redirect the user to MainActivity
            val toUpdate = Intent(this, MainActivity::class.java)

            toUpdate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            toUpdate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            toUpdate.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(toUpdate)
            finish()

        })
    }
}
