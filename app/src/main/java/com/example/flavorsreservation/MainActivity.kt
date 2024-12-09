package com.example.flavorsreservation

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flavorsreservation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private var userId: Int = 0
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        binding.rvReservations.layoutManager = LinearLayoutManager(this)
        loadReservations()

        binding.btnCreateReservation.setOnClickListener {
            val fecha = binding.etFecha.text.toString()
            val hora = binding.etHora.text.toString()
            val nombreUsuario = binding.etNombreUsuario.text.toString()

            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put("user_id", userId)
                put("nombre_usuario", nombreUsuario)
                put("fecha", fecha)
                put("hora", hora)
            }

            db.insert("reservations", null, values)
            loadReservations()
        }
    }

    private fun loadReservations() {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM reservations WHERE user_id = ?", arrayOf(userId.toString()))

        val reservations = mutableListOf<Reservation>()
        if (cursor.moveToFirst()) {
            do {
                val columnIndexId = cursor.getColumnIndex("id")
                val columnIndexNombreUsuario = cursor.getColumnIndex("nombre_usuario")
                val columnIndexFecha = cursor.getColumnIndex("fecha")
                val columnIndexHora = cursor.getColumnIndex("hora")

                if (columnIndexId != -1 && columnIndexNombreUsuario != -1 && columnIndexFecha != -1 && columnIndexHora != -1) {
                    val id = cursor.getInt(columnIndexId)
                    val nombreUsuario = cursor.getString(columnIndexNombreUsuario)
                    val fecha = cursor.getString(columnIndexFecha)
                    val hora = cursor.getString(columnIndexHora)
                    reservations.add(Reservation(id, nombreUsuario, fecha, hora))
                } else {
                    Log.e("DB", "Una o más columnas no se encontraron en la tabla")
                }
            } while (cursor.moveToNext())
        } else {
            Log.e("DB", "No se encontraron reservas para el usuario")
        }

        cursor.close()

        binding.rvReservations.adapter = ReservationsAdapter(reservations)
    }
}
