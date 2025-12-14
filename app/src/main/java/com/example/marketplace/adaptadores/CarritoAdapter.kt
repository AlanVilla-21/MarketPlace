package com.example.marketplace.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R
import com.example.marketplace.BaseDeDatos.CarritoItemRoom

class CarritoAdapter(
    private var lista: List<CarritoItemRoom>,
    private val context: Context
) : RecyclerView.Adapter<CarritoAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.imgCarrito)
        val tvNombre: TextView = v.findViewById(R.id.tvNombreCarrito)
        val tvCantidad: TextView = v.findViewById(R.id.tvCantidadCarrito)
        val tvPrecio: TextView = v.findViewById(R.id.tvPrecioCarrito)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_carrito, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        holder.tvNombre.text = item.nombre
        holder.tvCantidad.text = "Cantidad: ${item.cantidad}"
        holder.tvPrecio.text = "Bs. ${item.precio * item.cantidad}"

        val idImagen = context.resources.getIdentifier(item.imagen, "drawable", context.packageName)
        holder.img.setImageResource(idImagen)
    }

    fun actualizarLista(nueva: List<CarritoItemRoom>) {
        lista = nueva
        notifyDataSetChanged()
    }
}