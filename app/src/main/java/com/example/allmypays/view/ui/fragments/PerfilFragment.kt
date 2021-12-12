package com.example.allmypays.view.ui.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.allmypays.NavigationHost
import com.example.allmypays.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_consultar_perfil.*
import kotlinx.android.synthetic.main.fragment_registro.*
import kotlinx.android.synthetic.main.perfil_fragment.*
import kotlinx.android.synthetic.main.perfil_fragment.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PerfilFragment(userUid: String) : Fragment() {
    private lateinit var auth: FirebaseUser
    val db = FirebaseFirestore.getInstance()
    val userUid = userUid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance().currentUser!!

        db.collection("users").document(userUid).get()
            .addOnSuccessListener {
                txtINamePerfil.setText(it.get("name") as String?)
                txtIEmailPerfil.setText(it.get("email") as String?)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.perfil_fragment, container, false)
        view.Cancelar.setOnClickListener {
            (activity as NavigationHost).navigateTo(HomeFragment(userUid), false)
            view.visibility = View.GONE

        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        confirmarEdit.setOnClickListener {
            if (txtINamePerfil.text.toString()!!.isNotBlank() ||
                txtIEmailPerfil.text.toString()!!.isNotBlank()) {
                db.collection("users").document(userUid).set(
                    hashMapOf("name" to txtINamePerfil.text.toString(),
                        "email" to txtIEmailPerfil.text.toString()))
            }

            if(txtIpassword.text.toString()!!.isNotBlank() &&
                txtIpasswordCPerfil.text.toString()!!.isNotBlank()){
                if(txtIpassword.text.toString().equals(txtIpasswordCPerfil.text.toString())){
                    auth.updatePassword(txtIpassword.text.toString()).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                activity,
                                "Contraseña actualizada correctamente",
                                Toast.LENGTH_LONG
                            ).show()
                    }
                    }
                }else{
                    Toast.makeText(
                        activity,
                        "Las contraseñas no coinciden",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
    }


    private fun isValidString(text: Editable?): Boolean {
        return text != null && android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
    }

    private fun isPasswordValid(text: Editable?): Boolean {
        return text != null && text.length >= 8
    }
}