package com.example.marketplace.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.BaseDeDatos.CompraDetalleRoom
import com.example.marketplace.databinding.ItemCompraDetalleBinding

class CompraDetalleAdapter : RecyclerView.Adapter<CompraDetalleAdapter.DetalleCardViewHolder>() {

    private val dataCards = mutableListOf<CompraDetalleRoom>()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalleCardViewHolder {
        context = parent.context
        return DetalleCardViewHolder(
            ItemCompraDetalleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DetalleCardViewHolder, position: Int) {
        holder.binding(dataCards[position])
    }

    override fun getItemCount(): Int = dataCards.size

    inner class DetalleCardViewHolder(private val binding: ItemCompraDetalleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(data: CompraDetalleRoom) {
            binding.tvNombreDetalle.text = data.nombre
            binding.tvCantidadDetalle.text = "Cantidad: ${data.cantidad}"
            binding.tvSubtotalDetalle.text = "Bs. ${data.precio * data.cantidad}"

            val idImagen = context?.resources?.getIdentifier(data.imagen, "drawable", context?.packageName)
            if (idImagen != null && idImagen != 0) {
                binding.imgDetalle.setImageResource(idImagen)
            }

            manejoDeDatos()
        }

        fun manejoDeDatos() {}
    }

    fun addDataCards(list: List<CompraDetalleRoom>) {
        dataCards.clear()
        dataCards.addAll(list)
        notifyDataSetChanged()
    }
}
