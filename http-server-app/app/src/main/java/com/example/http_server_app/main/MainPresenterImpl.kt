package com.example.http_server_app.main

import com.example.http_server_app.db.AppDatabase
import com.example.http_server_app.db.Chat
import com.example.http_server_app.db.Message
import com.example.http_server_app.db.User
import com.example.http_server_app.model.ChatModel
import com.google.gson.Gson
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.InetSocketAddress
import java.util.*
import java.util.concurrent.Executors

class MainPresenterImpl(var db: AppDatabase) : MainContract.Presenter {

    private val PORT: Int = 8888
    private var serverRunning: Boolean = false
    private lateinit var httpServer: HttpServer

    override fun isServerRunning(): Boolean {
        return serverRunning
    }

    override fun stopServer() {
//        GlobalScope.launch {
//            db.getMessageDao().truncate()
//            db.getChatDao().truncate()
//        }
        httpServer.stop(0)
        serverRunning = false
    }

    override fun startServer() {
        try {
            httpServer = HttpServer.create(InetSocketAddress(PORT), 0)
            httpServer.executor = Executors.newCachedThreadPool()

            httpServer.createContext("/", rootHandler)
            httpServer.createContext("/register", registerHandler)
            httpServer.createContext("/user", userHandler)
            httpServer.createContext("/chat", chatHandler)
            httpServer.createContext("/message", messageHandler)
            httpServer.start()

            serverRunning = true
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private val rootHandler = HttpHandler { exchange ->
        run {
            when (exchange!!.requestMethod) {
                "GET" -> {
                    sendResponse(exchange, 200, "{\"status\": \"OK\"}")
                }
            }
        }
    }

    private val registerHandler = HttpHandler { exchange ->
        run {
            when (exchange!!.requestMethod) {
                "GET" -> {
                    val id = getQueryParams(exchange.requestURI.query)["id"] as String
                    val user = db.getUserDao().getUser(id)
                    sendResponse(exchange, 200, user)
                }
                "POST" -> {
                    val requestBody = streamToString(exchange.requestBody)
                    val jsonBody = JSONObject(requestBody)

                    val newUser = User(
                        jsonBody.getString("id"),
                        jsonBody.getString("nickname"),
                        jsonBody.getString("description"),
                        jsonBody.getString("image")
                    )

                    db.getUserDao().insertUser(newUser)

                    sendResponse(exchange, 200, newUser)
                }
            }
        }
    }

    private val userHandler = HttpHandler { exchange ->
        run {
            when (exchange!!.requestMethod) {
                "GET" -> {
                    val users = db.getUserDao().loadUsers()
                    sendResponse(exchange, 200, users)
                }
            }
        }
    }

    private val chatHandler = HttpHandler { exchange ->
        run {
            when (exchange!!.requestMethod) {
                "GET" -> {
                    val queryParams = getQueryParams(exchange.requestURI.query)
                    val userId = queryParams["userId"] as String
                    val page = (queryParams["page"] ?: error("")).toInt()
                    val limit = (queryParams["limit"] ?: error("")).toInt()

                    val chats = db.getChatDao().loadChatsByUser(userId, limit, (page - 1) * limit)

                    sendResponse(exchange, 200, chats.map { getChatModel(it) })
                }
                "POST" -> {
                    val requestBody = streamToString(exchange.requestBody)
                    val jsonBody = JSONObject(requestBody)

                    val date = Date()
                    val newChat = Chat(
                        fromUserId = jsonBody.getString("from"),
                        toUserId = jsonBody.getString("to"),
                        lastMessageDate = date
                    )

                    val chatId = db.getChatDao().insertChat(newChat)

                    val newMessage = Message(
                        chatId = chatId.toInt(),
                        userId = jsonBody.getString("from"),
                        text = jsonBody.getString("text"),
                        date = date
                    )

                    db.getMessageDao().insertMessage(newMessage)

                    val chatModel = ChatModel(
                        id = chatId.toInt(),
                        fromUser = db.getUserDao().getUser(newChat.fromUserId),
                        toUser = db.getUserDao().getUser(newChat.toUserId),
                        message = newMessage
                    )

                    sendResponse(exchange, 200, chatModel)
                }
                "DELETE" -> {
                    val queryParams = getQueryParams(exchange.requestURI.query)
                    val id = (queryParams["id"] as String).toInt()

                    db.getMessageDao().deleteMessages(id)

                    db.getChatDao().deleteChat(id)
                }
            }
        }
    }

    private fun getChatModel(newChat: Chat): ChatModel {
        val res = db.getMessageDao().getLastChatMessage(newChat.id)
        val aa = db.getMessageDao().loadAllMessages()
        return ChatModel(
            id = newChat.id,
            fromUser = db.getUserDao().getUser(newChat.fromUserId),
            toUser = db.getUserDao().getUser(newChat.toUserId),
            message = res
        )
    }

    private val messageHandler = HttpHandler { exchange ->
        run {
            when (exchange!!.requestMethod) {
                "GET" -> {
                    val chatId = (getQueryParams(exchange.requestURI.query)["chatId"] ?: error("")).toInt()
                    val messages = db.getMessageDao().loadMessages(chatId)
                    sendResponse(exchange, 200, messages)
                }
                "POST" -> {
                    val requestBody = streamToString(exchange.requestBody)
                    val jsonBody = JSONObject(requestBody)

                    val date = Date()
                    val chatId = jsonBody.getInt("chatId")
                    val text = jsonBody.getString("text")

                    val newMessage = Message(
                        chatId = chatId,
                        userId = jsonBody.getString("userId"),
                        text = text,
                        date = date
                    )

                    db.getMessageDao().insertMessage(newMessage)

                    val chat = db.getChatDao().getChat(chatId)
                    chat.lastMessageDate = date
                    db.getChatDao().updateChat(chat)

                    sendResponse(exchange, 200, newMessage)
                }
            }
        }
    }

    private fun streamToString(inputStream: InputStream): String {
        val s = Scanner(inputStream).useDelimiter("\\A")
        return if (s.hasNext()) s.next() else ""
    }

    private fun sendResponse(httpExchange: HttpExchange, status: Int, responseObj: Any) {
        val responseText = Gson().toJson(responseObj)
        httpExchange.sendResponseHeaders(status, responseText.length.toLong())
        val os = httpExchange.responseBody
        os.write(responseText.toByteArray())
        os.close()
    }

    private fun getQueryParams(query: String) : Map<String, String> {
        val m = HashMap<String, String>()
        query.split("&").forEach {
            val res = it.split("=")
            m[res[0]] = res[1]
        }
        return m
    }
}