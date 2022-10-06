package org.d3if4097.mobpro2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.d3if4097.mobpro2.databinding.ItemMainBinding
import org.d3if4097.mobpro2.model.Harian
import java.text.SimpleDateFormat
import java.util.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private val formatter = SimpleDateFormat("dd MMMM", Locale("ID", "id"))
    private val data = mutableListOf<Harian>()
    fun setData(newData: List<Harian>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMainBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[data.size - position - 1])
    }
    inner class ViewHolder(
        private val binding: ItemMainBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(harian: Harian) = with(binding) {
            tanggalTextView.text = formatter.format(Date(harian.key))
            positifTextView.text = itemView.context.getString(R.string.x_orang,
                harian.jumlahPositif.value)
        }
    }
}