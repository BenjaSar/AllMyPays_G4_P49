package data

import android.app.Person

class Tables {
    abstract class information{
        companion object{
            val _id = "id"
            val _nombre = "name"
            val _correoE = "email"
            val _password = "password"
            val _passwordC = "password_confirm"
            val TABLE_NAME = "info"
            val _persons: MutableList<Person> = ArrayList()
        }
    }
}

