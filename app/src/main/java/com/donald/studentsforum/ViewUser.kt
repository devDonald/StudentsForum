package com.donald.studentsforum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.donald.studentsforum.helpers.DatabaseHelper
import com.donald.studentsforum.model.Users

class ViewUser : AppCompatActivity() {

    private lateinit var tv_name:TextView
    private lateinit var tv_email:TextView
    private lateinit var tv_address:TextView
    private lateinit var tv_password:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_user)

        val id = intent.getIntExtra("id",-1)
        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val address = intent.getStringExtra("address")
        val password = intent.getStringExtra("password")


        tv_name = findViewById(R.id.view_user_name)
        tv_email = findViewById(R.id.view_user_email)
        tv_address = findViewById(R.id.view_user_address)
        tv_password = findViewById(R.id.view_user_password)

        tv_name.setText(name)
        tv_email.setText(email)
        tv_address.setText(address)
        tv_password.setText(password)

        val update_bt:Button = findViewById(R.id.view_user_update)
        val delete_bt:Button = findViewById(R.id.view_user_delete)

        //TODO 1: Add Lines 50 to 62 in your ViewUser Activity
        update_bt.setOnClickListener(View.OnClickListener {

            //redirect the user to MainActivity
            val toUpdate = Intent(this,UpdateUser::class.java)

            //sends id, name, email, address and password to the next page
            toUpdate.putExtra("id", id)
            toUpdate.putExtra("name",name)
            toUpdate.putExtra("email", email)
            toUpdate.putExtra("address", address)
            toUpdate.putExtra("password",password)

            startActivity(toUpdate)

        })



        delete_bt.setOnClickListener(View.OnClickListener {
            //create the database helper instance
            val db_helper = DatabaseHelper(this)

            val users = Users(id = id,name = "",email = "", password = "",address = "")
            db_helper.deleteUser(users)

            //redirect the user to MainActivity
            val toMain = Intent(this,MainActivity::class.java)
            toMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            toMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            toMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(toMain)
            finish()

            Toast.makeText(this,"User Deleted Successfully", Toast.LENGTH_LONG)
                .show()
        })
    }
}

