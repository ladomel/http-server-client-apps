package com.example.http_clinet_app.chat

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.http_clinet_app.R
import com.example.http_clinet_app.model.Chat
import com.example.http_clinet_app.model.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ChatFragment : Fragment(), ChatContract.View {

    private lateinit var userId: String
    private lateinit var presenter: ChatContract.Presenter
    private lateinit var chats: List<Chat>
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = ChatPresenterImpl(this)

        val v = inflater.inflate(R.layout.fragment_chat, container, false)

        recyclerView = v.findViewById(R.id.recyclerView)

        userId = activity?.getPreferences(Context.MODE_PRIVATE)
            ?.getString("UserId", "") ?: ""

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(recyclerView.context)
        }

        v.findViewById<FloatingActionButton>(R.id.addChat).setOnClickListener {
            presenter.loadUsers()
        }

        presenter.loadChats(userId)

        return v
    }

    override fun showError(text: String) {
        Snackbar.make(requireView(), text, Snackbar.LENGTH_INDEFINITE).show()
    }

    override fun gotoMessagePage(chat: Chat) {
        requireView().findNavController()
            .navigate(ChatFragmentDirections.actionChatFragmentToMessagesFragment(chat = chat))
    }

    override fun setData(data: List<Chat>) {
        chats = data
        recyclerView.swapAdapter(ChatAdapter(
            currentUserId = userId,
            data = data,
            clickHandler = {
                gotoMessagePage(it)
            },
            deleteEntryHandler = {},
            refreshHandler = {}
        ), true)
    }

    override fun showUserDialog(users: List<User>) {
        val inChatUserIds = chats.map { it.fromUser.id }.toMutableSet()
        inChatUserIds.addAll(chats.map { it.toUser.id })
        inChatUserIds.add(userId)

        val u = users.filter { !inChatUserIds.contains(it.id) }.map { it.nickname }

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Say Hi to:")

        builder.setItems(u.toTypedArray()) { dialog, which ->
            presenter.createChat(userId, users.filter { it.nickname == u[which] }[0].id)
        }

        // create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
    }

}