package com.example.http_clinet_app.messages

import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.example.http_clinet_app.R
import com.example.http_clinet_app.model.Message
import com.example.http_clinet_app.util.DateUtil
import kotlinx.android.synthetic.main.single_message_view.view.*

class MessagesAdapter(
    private var currentUserId: String,
    private var data: List<Message>,
    private var scrollView: NestedScrollView
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
            holder.outbox.visibility = View.VISIBLE
            holder.outboxText.text = value.text
            holder.outboxTime.text = DateUtil.getTimeElapsed(value.date)

            if (position == data.size - 1) holder.outbox.requestFocus()
        } else {
            holder.outbox.visibility = View.GONE
            holder.inbox.visibility = View.VISIBLE
            holder.inboxText.text = value.text
            holder.inboxTime.text = DateUtil.getTimeElapsed(value.date)

            if (position == data.size - 1) holder.inbox.requestFocus()
        }

        scrollView.fullScroll(ScrollView.FOCUS_DOWN)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}