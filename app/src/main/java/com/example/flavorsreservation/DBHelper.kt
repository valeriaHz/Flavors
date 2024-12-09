package com.example.flavorsreservation

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "reservaciones.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                correo TEXT,
                contrasena TEXT
            );
        """
        db?.execSQL(createUsersTable)

        val createReservationsTable = """
            CREATE TABLE IF NOT EXISTS reservations (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER,
                nombre_usuario TEXT,
                fecha TEXT,
                hora TEXT,
                FOREIGN KEY(user_id) REFERENCES users(id)
            );
        """
        db?.execSQL(createReservationsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS users")
        db?.execSQL("DROP TABLE IF EXISTS reservations")
        onCreate(db)
    }
}
