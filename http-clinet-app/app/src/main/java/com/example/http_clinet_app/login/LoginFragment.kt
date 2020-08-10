package com.example.http_clinet_app.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.http_clinet_app.R
import com.example.http_clinet_app.model.User
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment(), LoginContract.View {

    private lateinit var user: User
    private lateinit var presenter: LoginContract.Presenter

    private val args: LoginFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = LoginPresenterImpl(this)

        val v = inflater.inflate(R.layout.fragment_login, container, false)

        user = args.user ?: User(
            id = "",
            description = "",
            nickname = "",
            image = ""
        )

        val imageView = v.findViewById<ImageView>(R.id.image)
        val nicknameView = v.findViewById<EditText>(R.id.nickname)
        nicknameView.setText(user.nickname)
        val descView = v.findViewById<EditText>(R.id.description)
        descView.setText(user.description)
        v.findViewById<Button>(R.id.startButton).setOnClickListener {
            user.nickname = nicknameView.text.toString()
            user.description = descView.text.toString()
//            user.image = "aaaa"
            presenter.login(user)
        }

        return v
    }

    override fun gotoChats(user: User) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        if (sharedPref != null) {
            with(sharedPref.edit()) {
                putString("UserId", user.id)
                apply()
            }
        }

        requireView().findNavController()
            .navigate(LoginFragmentDirections.actionLoginFragmentToMessagesFragment(user = user))
    }

    override fun showMessage(text: String) {
        Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG).show()
    }

}