package com.example.marketplace.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.BaseDeDatos.ProductoRoom
import com.example.marketplace.databinding.ItemProductoBinding

class ProductoAdapter : RecyclerView.Adapter<ProductoAdapter.ProductoCardViewHolder>() {

    private val dataCards = mutableListOf<ProductoRoom>()
    private var context: Context? = null
    private var onClick: ((ProductoRoom) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoCardViewHolder {
        context = parent.context
        return ProductoCardViewHolder(
            ItemProductoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductoCardViewHolder, position: Int) {
        holder.binding(dataCards[position])
    }

    override fun getItemCount(): Int = dataCards.size

    inner class ProductoCardViewHolder(private val binding: ItemProductoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(data: ProductoRoom) {

            binding.tvNombre.text = data.nombre
            binding.tvCategoria.text = data.categoria
            binding.tvPrecio.text = "Bs. ${data.precio}"

            val idImagen = context?.resources?.getIdentifier(data.imagen, "drawable", context?.packageName)
            if (idImagen != null && idImagen != 0) {
                binding.imgProducto.setImageResource(idImagen)
            }

            binding.root.setOnClickListener {
                onClick?.invoke(data)
            }

            manejoDeDatos()
        }

        fun manejoDeDatos() {}
    }

    fun addDataCards(list: List<ProductoRoom>) {
        dataCards.clear()
        dataCards.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnClick(listener: (ProductoRoom) -> Unit) {
        onClick = listener
    }
}
