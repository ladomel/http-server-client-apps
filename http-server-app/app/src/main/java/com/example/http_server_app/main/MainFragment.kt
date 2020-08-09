package com.example.http_server_app.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.http_server_app.R
import com.example.http_server_app.db.AppDatabase
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var presenter: MainContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        presenter = MainPresenterImpl(AppDatabase.getInstance(context))

        val v = inflater.inflate(R.layout.fragment_main, container, false)

        val textView = v.findViewById<TextView>(R.id.text)
        v.findViewById<Button>(R.id.toggleServer).setOnClickListener {
            if (presenter.isServerRunning()) {
                presenter.stopServer()
                textView.text = "Server Stopped!!!"
            } else {
                presenter.startServer()
                textView.text = "Server Started!!!"
            }
        }

        return v
    }

}