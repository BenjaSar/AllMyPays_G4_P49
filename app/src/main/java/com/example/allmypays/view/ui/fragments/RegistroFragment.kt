package com.example.allmypays.view.ui.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.allmypays.NavigationHost
import com.example.allmypays.R
import com.example.allmypays.databinding.FragmentRegistroBinding
import data.DBHelper
import kotlinx.android.synthetic.main.fragment_registro.*
import kotlinx.android.synthetic.main.fragment_registro.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegistroFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistroFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    //Manejo de datos
    private var _binding: FragmentRegistroBinding? = null

    //Guardado y recuperacion de los datos
    private val binding get() = _binding!!
    //Variable para base de datos
    lateinit var informacionDBHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val viewRegister = inflater.inflate(R.layout.fragment_registro, container, false)

        viewRegister.register.setOnClickListener {
            if(!isValidString(txtEmailRegister.text!!) || !isPasswordValid(txtIpasswordRegister.text!!)){
                txtEmailRegister.error = "Por favor ingresa un email valido"
                txtIpasswordRegister.error = getString(R.string.error_password)

        } else{
                txtEmailRegister.error = null
                txtIpasswordRegister.error
                (activity as NavigationHost).navigateTo(LoginFragment(), false)
                viewRegister.visibility = View.GONE
            }
        }
        return viewRegister
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Configuracion almacenamiento de datos
        binding.register.setOnClickListener {
            if(binding.txtINameRegister.text!!.isNotBlank() &&
                    binding.txtEmailRegister.text!!.isNotBlank() &&
                    binding.txtIpasswordRegister.text!!.isNotBlank()&&
                    binding.txtIpasswordCRegister.text!!.isNotBlank()){
                //Conexion con la db
                informacionDBHelper.insert(binding.txtINameRegister.text.toString(),
                    binding.txtEmailRegister.text.toString(),
                    binding.txtIpasswordRegister.text.toString(),
                    binding.txtIpasswordCRegister.text.toString()
                )
                Toast.makeText(activity, "El registro fue efectuado de manera exitosa", Toast.LENGTH_LONG).show()
                //Limpieza de los campos editables
                binding.txtINameRegister.text!!.clear()
                binding.txtEmailRegister.text!!.clear()
                binding.txtIpasswordRegister.text!!.clear()
                binding.txtIpasswordCRegister.text!!.clear()
            } else{
                Toast.makeText(activity, "Error al realizar el registro del usuario", Toast.LENGTH_LONG).show()
            }
        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegistroFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistroFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

private fun isPasswordValid(text: Editable?): Boolean{
    return text != null && text.length>=8
}
private fun isValidString(text: Editable?):Boolean{
    return text!=null && android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
}