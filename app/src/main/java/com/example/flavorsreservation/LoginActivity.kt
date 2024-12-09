package com.example.flavorsreservation

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flavorsreservation.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val correo = binding.etCorreo.text.toString()
            val contrasena = binding.etContrasena.text.toString()

            val dbHelper = DBHelper(this)
            val db = dbHelper.readableDatabase

            val cursor: Cursor = db.rawQuery(
                "SELECT * FROM users WHERE correo = ? AND contrasena = ?",
                arrayOf(correo, contrasena)
            )

            if (cursor.moveToFirst()) {
                val idColumnIndex = cursor.getColumnIndex("id")
                val nombreColumnIndex = cursor.getColumnIndex("nombre")

                if (idColumnIndex != -1 && nombreColumnIndex != -1) {
                    val userId = cursor.getInt(idColumnIndex)
                    val nombre = cursor.getString(nombreColumnIndex)
                    Toast.makeText(this, "Bienvenido, $nombre", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Error: columnas no encontradas", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
