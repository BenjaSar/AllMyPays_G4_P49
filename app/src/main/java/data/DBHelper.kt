package data

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.fragment.app.FragmentActivity

class DBHelper(context:FragmentActivity?):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATA_VERSION) {
    companion object {
        private val DATABASE_NAME = "info"
        private val DATA_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(
            "CREATE TABLE" + Tables.information.TABLE_NAME + "(" +
                    Tables.information._id + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Tables.information._nombre + "TEXT NOT NULL," +
                    Tables.information._correoE + "TEXT NOT NULL," +
                    Tables.information._password + "TEXT NOT NULL," +
                    Tables.information._passwordC + "TEXT NOT NULL, " +
                    ")"
        )

    }
    fun insert(name:String, email:String, password:String, passwordC:String){
        val data= ContentValues()

        data.put(Tables.information._nombre, name)
        data.put(Tables.information._correoE, email)
        data.put(Tables.information._password, password)
        data.put(Tables.information._passwordC, passwordC)

        val db = this.writableDatabase
        db.insert(Tables.information.TABLE_NAME, null, data)
        db.close()
    }

    fun edit(Id:Int, name: String, password: String, passwordC: String){
        val args = arrayOf(Id.toString())
        val data = ContentValues()


    }
    }


