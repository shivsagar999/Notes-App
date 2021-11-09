package com.eurofins.mynotesapp.trash

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.eurofins.mynotesapp.NoteApplication
import com.eurofins.mynotesapp.R
import com.eurofins.mynotesapp.adapter.TrashNoteListAdapter
import com.eurofins.mynotesapp.databinding.FragmentTrashBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class TrashFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    lateinit var trashNoteAdapter: TrashNoteListAdapter
    var actionMode: ActionMode? = null

    private val trashFragmentViewModel: TrashViewModel by activityViewModels {
        TrashViewModelFactory((activity?.application as NoteApplication).database.getNotesDao())
    }

    private lateinit var binding: FragmentTrashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTrashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (trashFragmentViewModel.selectedPosition.isNotEmpty()) {
            if (actionMode == null) {
                actionMode = activity?.startActionMode(actionModeCallBack)
            }
        }

        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        trashNoteAdapter = TrashNoteListAdapter({
            Toast.makeText(context, "You can't edit the note in trash", Toast.LENGTH_SHORT).show()
        }, { trashNote, position ->

            if (actionMode == null) {
                actionMode = activity?.startActionMode(actionModeCallBack)
            }

            if (trashFragmentViewModel.selectedPosition.contains(position)) {
                trashFragmentViewModel.selectedPosition.remove(position)
                val selectedNote = recyclerView.layoutManager?.findViewByPosition(position)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    selectedNote?.setBackgroundColor(resources.getColor(R.color.light_black,
                        activity?.theme))
                } else {
                    selectedNote?.setBackgroundColor(resources.getColor(R.color.light_black))
                }
            } else {
                trashFragmentViewModel.selectedPosition[position] = trashNote
                val selectedNote = recyclerView.layoutManager?.findViewByPosition(position)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    selectedNote?.setBackgroundColor(resources.getColor(R.color.blue,
                        activity?.theme))
                } else {
                    selectedNote?.setBackgroundColor(resources.getColor(R.color.blue))
                }
            }
            if (trashFragmentViewModel.selectedPosition.isEmpty()) {
                actionMode?.finish()
                trashNoteAdapter.isSelected = false
            } else {
                trashNoteAdapter.isSelected = true
            }
            actionMode?.invalidate()

        })

        recyclerView.adapter = trashNoteAdapter
        lifecycle.coroutineScope.launch {
            trashFragmentViewModel.getAllTrashNotes().collect {
                trashNoteAdapter.submitList(it)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        recyclerView.viewTreeObserver.addOnGlobalLayoutListener {
            for (pos in trashFragmentViewModel.selectedPosition.keys) {
                val view = recyclerView.layoutManager?.findViewByPosition(pos)
                view?.setBackgroundColor(Color.parseColor("#887B06"))
                Log.d("Wagle", "Your View is $view")
            }
        }
    }

    private val actionModeCallBack = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            val inflater: MenuInflater = mode.menuInflater
            inflater.inflate(R.menu.trash_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            val menuItem = menu.findItem(R.id.selected_size)
            if (trashFragmentViewModel.selectedPosition.isEmpty()) {
                menuItem.title = R.string._0.toString()
            } else {
                menuItem.title = trashFragmentViewModel.selectedPosition.size.toString()
            }
            return true
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.restore -> {
                    for (trashNote in trashFragmentViewModel.selectedPosition.values) {
                        trashFragmentViewModel.deleteTrashNote(trashNote)
                        trashFragmentViewModel.addNoteToNotesTable(trashNote)
                    }
                    trashFragmentViewModel.selectedPosition.clear()
                    trashNoteAdapter.isSelected = false
                    mode.finish()
                    true
                }

                R.id.delete -> {
                    for (trashNote in trashFragmentViewModel.selectedPosition.values) {
                        trashFragmentViewModel.deleteTrashNote(trashNote)
                    }
                    trashFragmentViewModel.selectedPosition.clear()
                    trashNoteAdapter.isSelected = false
                    mode.finish()
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            actionMode = null
            trashNoteAdapter.isSelected = false
            unselectAllNotes(trashFragmentViewModel.selectedPosition.keys)
            trashFragmentViewModel.selectedPosition.clear()
        }
    }

    fun unselectAllNotes(keys: MutableSet<Int>) {
        for (pos in keys) {
            val view = recyclerView.layoutManager?.findViewByPosition(pos)
            view?.setBackgroundColor(Color.parseColor("#2B3131"))
            Log.d("Wagle", "Unselect View is $view")
        }
    }
}