package com.example.allmypays.view.ui.fragments


import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.*
import android.view.View.GONE
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.allmypays.*
import com.example.allmypays.databinding.LoginFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.view.*


class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: LoginFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding = LoginFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.login_fragment, container, false)

        view.backArrow.setOnClickListener {
            (activity as NavigationHost).navigateTo(MainFragment(), false)
            view.visibility = View.GONE
        }


        //view.login.setOnClickListener {

        /*view.login.setOnClickListener {
>>>>>>> 4856975dc0a40031ce9df54817f54efec5330a15
            if (!isPasswordValid(passwordEditText.text!!) || !isValidString(etMail.text)) {
                password.error = getString(R.string.error_password)
                etMail.error = "Por favor ingresa un email valido"
            } else {
                //Clear the error
                password.error = null
                etMail.error = null
                // Navigate to the next Fragment.
            (activity as NavigationHost).navigateTo(HomeFragment(), false)
                view.visibility = GONE
            }
        }


        // Clear the error once more than 8 characters are typed.
        view.passwordEditText.setOnKeyListener { _, _, _ ->
            if (isPasswordValid(passwordEditText.text!!)) {
                // Clear the error.
                password.error = null
            }
            false
        }*/
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginBtn = view.findViewById<View>(R.id.login)

        loginBtn.setOnClickListener {
            val email = etMail.text.toString()
            val password = passwordEditText.text.toString()
            if (email!!.isNotBlank() && password.isNotBlank()) {
                auth.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                       // findNavController().navigate(R.id.navhomeFragment)
                       (activity as NavigationHost).navigateTo(HomeFragment(), false)
                        view.findViewById<ConstraintLayout>(R.id.loginFragment).visibility= GONE

                    } else {
                        Toast.makeText(
                            activity,
                            "El usuario no se encuentra registrado",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }else{
                Toast.makeText(
                    activity,
                    "Error al realizar la autentificación del usuario",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}




// "isPasswordValid" from "Navigate to the next Fragment" section method goes here
private fun isPasswordValid(text:Editable?): Boolean{
    return text != null && text.length>=8
}
private fun isValidString(text:Editable?):Boolean{
    return text!=null && android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
}

