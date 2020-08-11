package com.example.http_clinet_app.chat

import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.http_clinet_app.R
import com.example.http_clinet_app.model.Chat
import com.example.http_clinet_app.util.DateUtil
import kotlinx.android.synthetic.main.single_chat_view.view.*
import java.text.SimpleDateFormat

class ChatAdapter(
    private var currentUserId: String,
    private var data: List<Chat>,
    private var clickHandler: (Chat) -> Unit,
    private var refreshHandler: (Int) -> Unit,
    private var deleteEntryHandler: (Int) -> Unit
) :
    RecyclerView.Adapter<ChatAdapter.ChatHolder>() {

    class ChatHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.image
        val name: TextView = view.name
        val text: TextView = view.text
        val time: TextView = view.time
        val wholeView: LinearLayout = view.wholeView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        return ChatHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.single_chat_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        val value = data[position]

        val user = if (value.toUser.id == currentUserId ) value.fromUser else value.toUser

        if (!user.image.isNullOrEmpty() && user.image != "null") {
            Glide.with(holder.image.context)
                .load(user.image)
                .centerCrop()
                .into(holder.image)
        }

        holder.name.text = user.nickname
        holder.text.text = value.message?.text ?: ""

        holder.time.text = value.message?.date?.let { DateUtil.getTimeElapsed(it) }

        holder.wholeView.setOnClickListener {
            clickHandler(value)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}