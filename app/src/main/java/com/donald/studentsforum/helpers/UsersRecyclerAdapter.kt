package com.donald.studentsforum.helpers

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.donald.studentsforum.R
import com.donald.studentsforum.ViewUser
import com.donald.studentsforum.model.Users
import kotlinx.android.synthetic.main.users_list_card.view.*
import org.w3c.dom.Text

class UsersRecyclerAdapter(private val listUsers:List<Users>) : RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        //inflating recycler item view
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.users_list_card, parent, false)

        return UserViewHolder(itemView)
    }

    override fun getItemCount(): Int {

        return listUsers.size

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        holder.textName.text = listUsers[position].name
        holder.textEmail.text = listUsers[position].email


    }


    inner class UserViewHolder(view: View):RecyclerView.ViewHolder(view){

       var textName: TextView
       var textEmail: TextView

       init {
           textName = view.findViewById<View>(R.id.user_name) as TextView
           textEmail = view.findViewById<View>(R.id.user_email) as TextView



       }



   }

}