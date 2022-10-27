package org.d3if4097.mobpro2

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if4097.mobpro2.data.Mahasiswa
import org.d3if4097.mobpro2.databinding.ItemMainBinding

class MainAdapter() : ListAdapter<Mahasiswa, MainAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Mahasiswa>() {
            override fun areItemsTheSame(
                oldData: Mahasiswa, newData: Mahasiswa
            ): Boolean {
                return oldData.id == newData.id
            }
            override fun areContentsTheSame(
                oldData: Mahasiswa, newData: Mahasiswa
            ): Boolean {
                return oldData == newData
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMainBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class ViewHolder(
        private val binding: ItemMainBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mahasiswa: Mahasiswa) {
            binding.nimTextView.text = mahasiswa.nim
            binding.namaTextView.text = mahasiswa.nama
        }
    }
}