package com.example.http_clinet_app.messages

import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.http_clinet_app.R
import com.example.http_clinet_app.model.Message
import kotlinx.android.synthetic.main.single_message_view.view.*
import java.text.SimpleDateFormat

class MessagesAdapter(
    private var currentUserId: String,
    private var data: List<Message>
) :
    RecyclerView.Adapter<MessagesAdapter.MessagesHolder>() {

    class MessagesHolder(view: View) : RecyclerView.ViewHolder(view) {
        val inbox: LinearLayout = view.inbox
        val inboxText: TextView = view.inbox.text
        val inboxTime: TextView = view.inbox.time

        val outbox: LinearLayout = view.outbox
        val outboxText: TextView = view.outbox.text
        val outboxTime: TextView = view.outbox.time
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesHolder {
        return MessagesHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.single_message_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MessagesHolder, position: Int) {
        val value = data[position]

        if (value.userId == currentUserId) {
            holder.inbox.visibility = View.GONE
            holder.outboxText.text = value.text
            holder.outboxTime.text = SimpleDateFormat("mm").format(value.date) + " min"
        } else {
            holder.outbox.visibility = View.GONE
            holder.inboxText.text = value.text
            holder.inboxTime.text = SimpleDateFormat("mm").format(value.date) + " min"
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}