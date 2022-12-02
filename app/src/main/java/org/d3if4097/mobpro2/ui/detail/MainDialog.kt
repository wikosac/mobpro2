package org.d3if4097.mobpro2.ui.detail

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import org.d3if4097.mobpro2.R
import org.d3if4097.mobpro2.data.Mahasiswa
import org.d3if4097.mobpro2.data.MahasiswaDb
import org.d3if4097.mobpro2.databinding.DialogMainBinding

class MainDialog : DialogFragment() {
    private lateinit var binding: DialogMainBinding

    private val viewModel: MainViewModel by lazy {
        val dataSource = MahasiswaDb.getInstance(requireContext()).dao
        val factory = MainViewModelFactory(dataSource)
        ViewModelProvider(requireActivity(), factory)
            .get(MainViewModel::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
    {
        val args = MainDialogArgs.fromBundle(requireArguments())
        Log.d("KELAS", args.kelas)

        val inflater = LayoutInflater.from(requireContext())
        binding = DialogMainBinding.inflate(inflater, null, false)
        val builder = AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.tambah_mahasiswa)
            setView(binding.root)
            setPositiveButton(R.string.simpan) { _, _ ->
                val mahasiswa = getData() ?: return@setPositiveButton
                viewModel.insertData(mahasiswa)
            }
            setNegativeButton(R.string.batal) { _, _ -> dismiss() }
        }
        return builder.create()
    }

    private fun getData(): Mahasiswa? {
        if (binding.nimEditText.text.isEmpty()) {
            showMessage(R.string.nim_wajib_diisi)
            return null
        }
        if (binding.nimEditText.text.length != 10) {
            showMessage(R.string.nim_harus_10chars)
            return null
        }
        if (binding.namaEditText.text.isEmpty()) {
            showMessage(R.string.nama_wajib_diisi)
            return null
        }

        return Mahasiswa(
            nim = binding.nimEditText.text.toString(),
            nama = binding.namaEditText.text.toString()
        )
    }

    private fun showMessage(messageResId: Int) {
        Toast.makeText(requireContext(), messageResId, Toast.LENGTH_LONG).apply {
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }
}