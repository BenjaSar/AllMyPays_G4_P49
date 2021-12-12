package com.example.allmypays.view.ui.fragments


import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.*
import android.view.View.GONE
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.allmypays.*
import com.example.allmypays.databinding.LoginFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.view.*


class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.login_fragment, container, false)

        view.backArrow.setOnClickListener {
            (activity as NavigationHost).navigateTo(MainFragment(), false)
            view.visibility = View.GONE
        }
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
                        val user: FirebaseUser = auth.currentUser!!
                       (activity as NavigationHost).navigateTo(HomeFragment(user.uid), false)
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

private fun isPasswordValid(text:Editable?): Boolean{
    return text != null && text.length>=8
}
private fun isValidString(text:Editable?):Boolean{
    return text!=null && android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
}

