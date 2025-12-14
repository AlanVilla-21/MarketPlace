package com.example.marketplace.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R
import com.example.marketplace.BaseDeDatos.ProductoRoom
import android.content.Intent
import com.example.marketplace.DetalleProducto



class ProductoAdapter(
    private var lista: List<ProductoRoom>,
    private val context: Context,
    private val onClick: (ProductoRoom) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.imgProducto)
        val tvNombre: TextView = v.findViewById(R.id.tvNombre)
        val tvCategoria: TextView = v.findViewById(R.id.tvCategoria)
        val tvPrecio: TextView = v.findViewById(R.id.tvPrecio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = lista[position]
        holder.tvNombre.text = p.nombre
        holder.tvCategoria.text = p.categoria
        holder.tvPrecio.text = "Bs. ${p.precio}"

        val idImagen = context.resources.getIdentifier(p.imagen, "drawable", context.packageName)
        holder.img.setImageResource(idImagen)

        holder.itemView.setOnClickListener {
            onClick(p)
        }
    }

    fun actualizarLista(nueva: List<ProductoRoom>) {
        lista = nueva
        notifyDataSetChanged()
    }
}