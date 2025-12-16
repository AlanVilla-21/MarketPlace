package com.example.marketplace.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.BaseDeDatos.CompraRoom
import com.example.marketplace.databinding.ItemCompraBinding

class CompraAdapter : RecyclerView.Adapter<CompraAdapter.CompraCardViewHolder>() {

    private val dataCards = mutableListOf<CompraRoom>()
    private var context: Context? = null
    private var onClick: ((CompraRoom) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompraCardViewHolder {
        context = parent.context
        return CompraCardViewHolder(
            ItemCompraBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CompraCardViewHolder, position: Int) {
        holder.binding(dataCards[position])
    }

    override fun getItemCount(): Int = dataCards.size

    inner class CompraCardViewHolder(private val binding: ItemCompraBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(data: CompraRoom) {
            binding.tvFechaCompra.text = data.fecha
            binding.tvTotalCompra.text = "Total: Bs. ${data.total}"

            binding.root.setOnClickListener {
                onClick?.invoke(data)
            }

            manejoDeDatos()
        }

        fun manejoDeDatos() {}
    }

    fun addDataCards(list: List<CompraRoom>) {
        dataCards.clear()
        dataCards.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnClick(listener: (CompraRoom) -> Unit) {
        onClick = listener
    }
}
