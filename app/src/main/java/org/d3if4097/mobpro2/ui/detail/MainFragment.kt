package org.d3if4097.mobpro2.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.d3if4097.mobpro2.R
import org.d3if4097.mobpro2.data.Mahasiswa
import org.d3if4097.mobpro2.data.MahasiswaDb
import org.d3if4097.mobpro2.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var myAdapter: MainAdapter
    private var actionMode: ActionMode? = null
    private val actionModeCallback = object : ActionMode.Callback {
        override fun onActionItemClicked(mode: ActionMode?,
                                         item: MenuItem?): Boolean {
            if (item?.itemId == R.id.menu_delete) {
                deleteData()
                return true
            }
            return false
        }
        override fun onCreateActionMode(mode: ActionMode?,
                                        menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.main_mode, menu)
            return true
        }
        override fun onPrepareActionMode(mode: ActionMode?,
                                         menu: Menu?): Boolean {
            mode?.title = myAdapter.getSelection().size.toString()
            return true
        }
        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
            myAdapter.resetSelection()
        }
    }
    private val handler = object : MainAdapter.ClickHandler {
        override fun onClick(position: Int, mahasiswa: Mahasiswa) {
            if (actionMode != null) {
                myAdapter.toggleSelection(position)
                if (myAdapter.getSelection().isEmpty())
                    actionMode?.finish()
                else
                    actionMode?.invalidate()
                return
            }
            val message = getString(R.string.mahasiswa_klik, mahasiswa.nama)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        override fun onLongClick(position: Int): Boolean {
            if (actionMode != null) return false
            myAdapter.toggleSelection(position)
            actionMode = (requireActivity() as AppCompatActivity)
                .startSupportActionMode(actionModeCallback)
            return true
        }
    }

    private fun deleteData() = AlertDialog.Builder(requireContext()).apply {
        setMessage(R.string.pesan_hapus)
        setPositiveButton(R.string.hapus) { _, _ ->
            viewModel.deleteData(myAdapter.getSelection())
            actionMode?.finish()
        }
        setNegativeButton(R.string.batal) { dialog, _ ->
            dialog.cancel()
            actionMode?.finish()
        }
        show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        val args = MainFragmentArgs.fromBundle(requireArguments())

        binding.fab.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToMainDialog(args.kelas)
            )
        }

        myAdapter = MainAdapter(handler)
        with(binding.recyclerView) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            setHasFixedSize(true)
            adapter = myAdapter
        }
        viewModel.getData(args.kelas).observe(viewLifecycleOwner, {
            myAdapter.submitList(it)
            binding.emptyView.visibility = if (it.isEmpty()) View.VISIBLE
            else View.GONE
        })
    }

    private val viewModel: MainViewModel by lazy {
        val dataSource = MahasiswaDb.getInstance(requireContext()).dao
        val factory = MainViewModelFactory(dataSource)
        ViewModelProvider(requireActivity(), factory)
            .get(MainViewModel::class.java)
    }

    override fun onDestroy() {
        actionMode?.finish()
        super.onDestroy()
    }
}