package com.donald.studentsforum.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.donald.studentsforum.model.Users

class DatabaseHelper(context:Context): SQLiteOpenHelper (context, DATABASE_NAME, null, DATABASE_VERSION) {

    // create a table that will perform our SQL Query

    private val CREATE_USER_TABLE  = ("CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_USER_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME+ " TEXT,"
            + COLUMN_USER_EMAIL+ " TEXT," + COLUMN_USER_PASSWORD+ " TEXT," + COLUMN_USER_ADDRESS+ " TEXT" +")"
            )

    //create query to drop our table

    private val DROP_USER_TABLE = " DROP TABLE IF EXISTS $TABLE_NAME"


    override fun onCreate(db: SQLiteDatabase?) {

        if (db != null) {
            db.execSQL(CREATE_USER_TABLE)
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

        if (p0 != null) {
            p0.execSQL(DROP_USER_TABLE)
        }
        onCreate(p0)

    }

    // create a function to add User Record
    fun addUsers(users:Users){

        //db is an instance of the writable database that aid us to write to or update our database
        val db = this.writableDatabase

        val values = ContentValues()

        values.put(COLUMN_USER_NAME,users.name)
        values.put(COLUMN_USER_EMAIL,users.email)
        values.put(COLUMN_USER_PASSWORD,users.password)
        values.put(COLUMN_USER_ADDRESS, users.address)

        db.insert(TABLE_NAME, null, values)

        db.close()

    }

    fun checkUser(email: String) : Boolean {

        //specifies the Array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID)
        val db = readableDatabase

        //write selection criteria

        val selection = "$COLUMN_USER_EMAIL = ?"


        //write selection Argument

        val selectionArgs = arrayOf(email)

        val cursor = db.query(TABLE_NAME,
           columns,
           selection,
            selectionArgs,
            null,
            null,
            null)

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount>0){

            return true
        }

        return false
    }

    fun checkUser(email: String, password: String): Boolean {
        //create array of columns to fetch from
        val columns = arrayOf(COLUMN_USER_ID)

        val db = this.readableDatabase

        // selection criteria
        val selection = "$COLUMN_USER_EMAIL = ? AND $COLUMN_USER_PASSWORD = ?"

        //selection arguments

        val selectionArgs = arrayOf(email, password)

        //query user table with conditions
        //here query() function is used to fetch record from the user table
        val cursor = db.query(TABLE_NAME,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null)

        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount>0){
            return true
        }
        return false
    }

    fun deleteUser(user: Users){
        val db = this.writableDatabase
        //delete user record by id
        db.delete(TABLE_NAME, "$COLUMN_USER_ID = ?",
            arrayOf(user.id.toString()))
    }

    fun updateUser(user: Users){

        val db = this.writableDatabase

        val values = ContentValues()

        values.put(COLUMN_USER_NAME, user.name)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_PASSWORD, user.password)
        values.put(COLUMN_USER_ADDRESS, user.address)

        db.update(TABLE_NAME, values, "$COLUMN_USER_ID = ?",
            arrayOf(user.id.toString()))

        db.close()
    }

    fun fetchUsers(): List<Users> {

        // array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_EMAIL,
            COLUMN_USER_PASSWORD, COLUMN_USER_ADDRESS)

        // sorting order

        val sortOrder = "$COLUMN_USER_NAME ASC"
        val userList = ArrayList<Users>()

        val db = this.readableDatabase

        // query the user table

        val cursor = db.query(TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            sortOrder)
        if (cursor.moveToFirst()){
            do {
                val user = Users(id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt(),
                    name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                    email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)),
                    password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)),
                    address = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ADDRESS)))

                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }


    companion object {
        private val DATABASE_VERSION =1

        private val DATABASE_NAME = "UsersDB.db"

        private val TABLE_NAME = "users"

        private val COLUMN_USER_ID = "user_id"
        private val COLUMN_USER_NAME = "user_name"
        private val COLUMN_USER_EMAIL = "user_email"
        private val COLUMN_USER_PASSWORD = "user_password"
        private val COLUMN_USER_ADDRESS = "user_address"

    }
}


