package com.donald.studentsforum

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.donald.studentsforum.helpers.DatabaseHelper
import com.donald.studentsforum.helpers.UsersRecyclerAdapter
import com.donald.studentsforum.model.Users

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var allUsersRecycler: RecyclerView
    private lateinit var listView : MutableList<Users>
    private lateinit var recyclerAdapter : UsersRecyclerAdapter
    private lateinit var databaseHelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        allUsersRecycler = findViewById<View>(R.id.all_users_recycler) as RecyclerView
        listView = ArrayList()
        recyclerAdapter = UsersRecyclerAdapter(listView)

        //val mLayoutManager = LinearLayoutManager(this)
        allUsersRecycler.layoutManager = LinearLayoutManager(this)

        allUsersRecycler.setHasFixedSize(true)

        //links your recycler adapter class to the the recyclerview on your xml file
        allUsersRecycler.adapter =recyclerAdapter


        databaseHelper = DatabaseHelper(this)


        GetDataFromSQLite().execute()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        val id = item.itemId

        if (id==R.id.action_logout){
            logout()
        }

        return super.onOptionsItemSelected(item)
    }

    fun logout(){

        val logoutPref =  getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)
        logoutPref.edit().clear().apply()

        val toLogin = Intent(this, LoginActivity::class.java)
        startActivity(toLogin)
        finish()
    }

    //this class is to fetch all user record from SQLite without lagging
    inner class GetDataFromSQLite: AsyncTask <Void, Void, List<Users>>(){

        override fun doInBackground(vararg p0: Void?): List<Users> {
            return databaseHelper.fetchUsers()

        }

        override fun onPostExecute(result: List<Users>?) {
            super.onPostExecute(result)

            listView.clear()

            listView.addAll(result!!)
        }

    }



}
