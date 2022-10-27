package org.d3if4097.mobpro2

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import org.d3if4097.mobpro2.databinding.DialogMainBinding

class MainDialog : DialogFragment() {
    private lateinit var binding: DialogMainBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(requireContext())
        binding = DialogMainBinding.inflate(inflater, null, false)
        val builder = AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.tambah_mahasiswa)
            setView(binding.root)
            setPositiveButton(R.string.simpan) { _, _ ->
                val listener = requireActivity() as DialogListener
                listener.processDialog()
            }
            setNegativeButton(R.string.batal) { _, _ -> dismiss() }
        }
        return builder.create()
    }

    interface DialogListener {
        fun processDialog()
    }
}