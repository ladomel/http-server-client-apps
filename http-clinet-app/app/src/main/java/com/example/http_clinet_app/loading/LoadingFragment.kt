package com.example.http_clinet_app.loading

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.http_clinet_app.R
import com.example.http_clinet_app.model.User
import com.google.android.material.snackbar.Snackbar

class LoadingFragment : Fragment(), LoadingContract.View {

    private lateinit var presenter: LoadingContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = LoadingPresenterImpl(this)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        if (sharedPref != null) {
            with(sharedPref.edit()) {
                putString("UserId", "")
                apply()
            }
        }

        val userId = activity?.getPreferences(Context.MODE_PRIVATE)
            ?.getString("UserId", null)

        presenter.findServer(userId)

        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    override fun showError(text: String) {
        Snackbar.make(requireView(), text, Snackbar.LENGTH_INDEFINITE).show()
    }

    override fun gotoLoginPage(user: User?) {
        requireView().findNavController().navigate(LoadingFragmentDirections.actionLoadingFragmentToLoginFragment(user = user))
    }

}