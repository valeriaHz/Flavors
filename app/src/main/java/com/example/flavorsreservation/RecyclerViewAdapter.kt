package com.example.flavorsreservation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flavorsreservation.databinding.ItemReservationBinding

data class Reservation(val id: Int, val nombreUsuario: String, val fecha: String, val hora: String)

class ReservationsAdapter(private val reservations: List<Reservation>) :
    RecyclerView.Adapter<ReservationsAdapter.ReservationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {

        val binding = ItemReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val reservation = reservations[position]
        holder.bind(reservation)
    }

    override fun getItemCount() = reservations.size

    class ReservationViewHolder(private val binding: ItemReservationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reservation: Reservation) {
            binding.tvNombreUsuario.text = reservation.nombreUsuario
            binding.tvFecha.text = reservation.fecha
            binding.tvHora.text = reservation.hora
        }
    }
}
