package com.example.allmypays.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.allmypays.NavigationHost
import com.example.allmypays.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_consultar_perfil.*
import kotlinx.android.synthetic.main.fragment_consultar_perfil.view.*


class ConsultarPerfilFragment(userUid: String) : Fragment() {

    val db = FirebaseFirestore.getInstance()
    val userUid = userUid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db.collection("users").document(userUid).get()
            .addOnSuccessListener {
                nombreCompleto.setText(it.get("name") as String?)
                UserEmail.setText(it.get("email") as String?)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val viewConsultarPerfil = inflater.inflate(R.layout.fragment_consultar_perfil, container, false)

        viewConsultarPerfil.backMain.setOnClickListener {
            (activity as NavigationHost).navigateTo(HomeFragment(userUid), false)
            viewConsultarPerfil.visibility = View.GONE
        }

        viewConsultarPerfil.EditarPerfil.setOnClickListener {
            (activity as NavigationHost).navigateTo(PerfilFragment(userUid), false)
            viewConsultarPerfil.visibility = View.GONE
        }
        return viewConsultarPerfil

    }

}