package com.example.flavorsreservation

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flavorsreservation.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val nombre = binding.etNombre.text.toString()
            val correo = binding.etCorreo.text.toString()
            val contrasena = binding.etContrasena.text.toString()

            val dbHelper = DBHelper(this)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put("nombre", nombre)
                put("correo", correo)
                put("contrasena", contrasena)
            }

            val newRowId = db.insert("users", null, values)
            if (newRowId != -1L) {
                finish()
            }
        }
    }
}
