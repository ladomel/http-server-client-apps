package com.example.http_clinet_app.messages

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.http_clinet_app.R
import com.example.http_clinet_app.model.Chat
import com.example.http_clinet_app.model.Message
import com.example.http_clinet_app.model.User
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MessagesFragment : Fragment(), MessagesContract.View {

    private lateinit var presenter: MessagesContract.Presenter
    private lateinit var userId: String
    private lateinit var otherUser: User
    private lateinit var chat: Chat
    private lateinit var recyclerView: RecyclerView
    private lateinit var messagesData: MutableList<Message>
    private lateinit var scrollView: NestedScrollView
    private val args: MessagesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        presenter = MessagesPresenterImpl(this)

        chat = args.chat!!
        userId = activity?.getPreferences(Context.MODE_PRIVATE)
            ?.getString("UserId", "") ?: ""

        val v = inflater.inflate(R.layout.fragment_messages, container, false)

        v.findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            requireView().findNavController().popBackStack()
        }

        otherUser = if (chat.fromUser.id == userId) chat.toUser else chat.fromUser

        v.findViewById<TextView>(R.id.nickname).text = otherUser.nickname
        v.findViewById<TextView>(R.id.description).text = otherUser.description

        val textField = v.findViewById<EditText>(R.id.messageText)

        v.findViewById<ImageButton>(R.id.send).setOnClickListener {
            presenter.sendMessage(Message(0, chat.id, userId, textField.text.toString(), Date()))
            textField.setText("")
            textField.clearFocus()

            val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

            scrollView.fullScroll(ScrollView.FOCUS_DOWN)
        }

        val imageView = v.findViewById<ImageView>(R.id.image)
        if (!otherUser.image.isNullOrEmpty() && otherUser.image != "null") {
            Glide.with(imageView.context)
                .load(otherUser.image)
                .centerCrop()
                .into(imageView)
        }

        recyclerView = v.findViewById(R.id.recyclerView)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(recyclerView.context)
        }

        scrollView = v.findViewById(R.id.scrollView)
        scrollView.post {
            scrollView.fullScroll(View.FOCUS_DOWN)
        }

        presenter.loadMessages(chat.id)

        return v
    }

    override fun showError(text: String) {
        Snackbar.make(requireView(), text, Snackbar.LENGTH_INDEFINITE).show()
    }

    override fun setData(data: List<Message>) {
        messagesData = data.toMutableList()
        recyclerView.swapAdapter(MessagesAdapter(
            currentUserId = userId,
            data = data,
            scrollView = scrollView
        ), true)
    }

    override fun refreshView(message: Message) {
        messagesData.add(message)
        setData(messagesData)
    }
}