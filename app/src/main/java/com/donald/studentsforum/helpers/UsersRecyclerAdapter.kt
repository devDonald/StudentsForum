package com.donald.studentsforum.helpers

import android.content.Context
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

class UsersRecyclerAdapter(private val listUsers:List<Users>, internal var context: Context) : RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder>() {

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

        //set onclick listener on a user's data
        holder.itemView.setOnClickListener(View.OnClickListener {

            val i = Intent(context, ViewUser::class.java)

            //pass the details of the user to the next activity

            i.putExtra("id", listUsers[position].id)
            i.putExtra("name",listUsers[position].name)
            i.putExtra("email",listUsers[position].email)
            i.putExtra("address", listUsers[position].address)
            i.putExtra("password", listUsers[position].password)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        })

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