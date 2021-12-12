package com.example.allmypays.view.ui.fragments

import android.database.sqlite.SQLiteDatabase
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
import com.example.allmypays.databinding.PerfilFragmentBinding
import data.DBHelper
import data.Tables
import kotlinx.android.synthetic.main.perfil_fragment.*
import kotlinx.android.synthetic.main.perfil_fragment.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PerfilFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PerfilFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //Manejo de datos
    private var _binding: PerfilFragmentBinding? = null

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
        val view =  inflater.inflate(R.layout.perfil_fragment, container, false)
        view.Cancelar.setOnClickListener {
            (activity as NavigationHost).navigateTo(HomeFragment(), false)
            view.visibility = View.GONE

        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonConfirmar = view.findViewById<Button>(R.id.confirmar)
        buttonConfirmar?.setOnClickListener {
            if(!isValidString(txtIEmailPerfil.text!!) || !isPasswordValid(txtIpassword.text!!)){
                txtIEmailPerfil.error = "Por favor ingresa un email valido"
                txtIpassword.error = getString(R.string.error_password)
            }
            else{
                txtIEmailPerfil.error = null
                txtIpassword.error = null
                findNavController().navigate(R.id.navperfilFragment)
            }
        }

      /*  binding.confirmar.setOnClickListener {
            if(binding.txtINamePerfil.text!!.isNotBlank() &&
                binding.txtIEmailPerfil.text!!.isNotBlank() &&
                binding.txtIpassword.text!!.isNotBlank() &&
                binding.txtIpasswordCPerfil.text!!.isNotBlank()
            ){
                //Conexion con la db
                informacionDBHelper.edit(1,binding.txtINamePerfil.toString(),
                    binding.txtIEmailPerfil.text.toString(),
                    binding.txtIpassword.text.toString(),
                    binding.txtIpasswordCPerfil.text.toString()
                )
                Toast.makeText(activity, "El registro fue actualizado de manera exitosa", Toast.LENGTH_LONG).show()
                //Limpieza de los campos editables
                binding.txtINamePerfil.text!!.clear()
                binding.txtIEmailPerfil.text!!.clear()
                binding.txtIpassword.text!!.clear()
                binding.txtIpasswordCPerfil.text!!.clear()
            }else{
                Toast.makeText(activity, "Error al realizar la actualización del usuario", Toast.LENGTH_LONG).show()
            }
            //Recuperacion de datos
            val db: SQLiteDatabase = informacionDBHelper.readableDatabase
            val cursor  = db.rawQuery("SELECT * FROM " + Tables.information.TABLE_NAME, null)

            if(cursor.moveToFirst()){
                do{
                    binding.txtINamePerfil.setText(cursor.getString(1).toString())
                    binding.txtIEmailPerfil.setText(cursor.getString(2).toString())
                    binding.txtIpassword.setText(cursor.getString(3).toString())
                    binding.txtIpasswordCPerfil.setText(cursor.getString(4).toString())
                }while (cursor.moveToNext())
            }

        }*/
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PerfilFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PerfilFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

private fun isValidString(text:Editable?):Boolean{
    return text!=null && android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
}
private fun isPasswordValid(text:Editable?): Boolean{
    return text != null && text.length>=8
}