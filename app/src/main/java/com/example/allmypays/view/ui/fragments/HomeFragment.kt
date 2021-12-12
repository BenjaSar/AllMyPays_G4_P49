package com.example.allmypays.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.allmypays.NavigationHost
import com.example.allmypays.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.view.*


class HomeFragment(uid: String) : Fragment() {


    val db = FirebaseFirestore.getInstance()
    val userUid = uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db.collection("users").document(userUid).get()
            .addOnSuccessListener {
                nombreTextView.setText(it.get("name") as String?)
            }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.home_fragment, container, false)
        view.image_19.setOnClickListener {
            (activity as NavigationHost).navigateTo(RecordatorioFragment(userUid), false)
            view.visibility = View.GONE
        }

       view.image_21.setOnClickListener {
           (activity as NavigationHost).navigateTo(ConsultarPerfilFragment(userUid), false)
           view.visibility = View.GONE
       }
        return view
    }

}