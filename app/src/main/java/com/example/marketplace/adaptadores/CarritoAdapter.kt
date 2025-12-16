package com.example.marketplace.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.BaseDeDatos.CarritoItemRoom
import com.example.marketplace.databinding.ItemCarritoBinding

class CarritoAdapter : RecyclerView.Adapter<CarritoAdapter.CarritoCardViewHolder>() {

    private val dataCards = mutableListOf<CarritoItemRoom>()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoCardViewHolder {
        context = parent.context
        return CarritoCardViewHolder(
            ItemCarritoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CarritoCardViewHolder, position: Int) {
        holder.binding(dataCards[position])
    }

    override fun getItemCount(): Int = dataCards.size

    inner class CarritoCardViewHolder(private val binding: ItemCarritoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(data: CarritoItemRoom) {

            binding.tvNombreCarrito.text = data.nombre
            binding.tvCantidadCarrito.text = "Cantidad: ${data.cantidad}"
            binding.tvPrecioCarrito.text = "Bs. ${data.precio * data.cantidad}"

            val idImagen = context?.resources?.getIdentifier(data.imagen, "drawable", context?.packageName)
            if (idImagen != null && idImagen != 0) {
                binding.imgCarrito.setImageResource(idImagen)
            }

            manejoDeDatos()
        }

        fun manejoDeDatos() {}
    }

    fun addDataCards(list: List<CarritoItemRoom>) {
        dataCards.clear()
        dataCards.addAll(list)
        notifyDataSetChanged()
    }


}
