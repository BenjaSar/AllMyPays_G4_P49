package com.example.allmypays.view.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.view.*
import android.view.View.GONE
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.allmypays.NavigationHost
import com.example.allmypays.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_registro.*
import kotlinx.android.synthetic.main.fragment_registro.view.*

class RegistroFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore


    //Guardado y recuperacion de los datos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val viewRegister = inflater.inflate(R.layout.fragment_registro, container, false)
        viewRegister.backToMain.setOnClickListener {
            (activity as NavigationHost).navigateTo(MainFragment(), false)
            viewRegister.visibility = View.GONE
        }
        return viewRegister
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val registerBtn = view.findViewById<View>(R.id.register)

        registerBtn.setOnClickListener{

            if (txtEmailRegister.text.toString()!!.isNotBlank() &&
                txtIpasswordRegister.text.toString()!!.isNotBlank() &&
                txtINameRegister.text.toString()!!.isNotBlank()) {

                auth.createUserWithEmailAndPassword(txtEmailRegister.text.toString()!!,
                    txtIpasswordRegister.text.toString()!!
                ).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user:FirebaseUser = auth.currentUser!!
                            db.collection("users").document(user.uid).set(
                                hashMapOf("name" to txtINameRegister.text.toString(),
                                    "email" to txtEmailRegister.text.toString()))
                            Toast.makeText(
                                activity,
                                "El registro fue efectuado de manera exitosa",
                                Toast.LENGTH_LONG
                            ).show()
                            //Limpieza de los campos editables
                            txtINameRegister.text.clear()
                            txtEmailRegister.text.clear()
                            txtIpasswordRegister.text.clear()
                            txtIpasswordCRegister.text.clear()
                            (activity as NavigationHost).navigateTo(LoginFragment(), false)
                            val f = view.findViewById<View>(R.id.fragmentRegistro)
                            f.visibility = GONE
                        } else {
                            Toast.makeText(
                                activity,
                                "El usuario ya se encuentra registrado",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

            } else {
                Toast.makeText(
                    activity,
                    "Error al realizar el registro del usuario",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    private fun isPasswordValid(text: Editable?): Boolean {
        return text != null && text.length >= 8
    }

    private fun isValidString(text: Editable?): Boolean {
        return text != null && android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
    }
}