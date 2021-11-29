package com.example.allmypays.ui.fragments


import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.allmypays.HomeFragment
import com.example.allmypays.NavigationHost
import com.example.allmypays.R
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.view.*

/**
 * Fragment representing the login screen for AllMyPays.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.login_fragment, container, false)
        //Evento next_button para validar la contraseña
        view.login.setOnClickListener {
            if (!isPasswordValid(password_edit_text.text!!)) {
                password.error = getString(R.string.error_password)
            } else {
                //Clear the error
                password.error = null
                // Navigate to the next Fragment.
                (activity as NavigationHost).navigateTo(HomeFragment(), false)
            }
        }

        // Clear the error once more than 8 characters are typed.
        view.password_edit_text.setOnKeyListener { _, _, _ ->
            if (isPasswordValid(password_edit_text.text!!)) {
                // Clear the error.
                password.error = null
            }
            false
        }
        return view
    }
}

// "isPasswordValid" from "Navigate to the next Fragment" section method goes here
private fun isPasswordValid(text:Editable?): Boolean{
    return text != null && text.length>=8
}
