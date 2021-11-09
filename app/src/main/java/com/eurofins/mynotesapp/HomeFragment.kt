package com.eurofins.mynotesapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.eurofins.mynotesapp.adapter.NoteListAdapter
import com.eurofins.mynotesapp.database.Note
import com.eurofins.mynotesapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var noteAdapter: NoteListAdapter
    private var searchNotes: ArrayList<Note> = ArrayList()

    var actionMode: ActionMode? = null

    private val homeFragmentViewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory((activity?.application as NoteApplication).database.getNotesDao())
    }
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (homeFragmentViewModel.selectedPosition.isNotEmpty()) {
            if (actionMode == null) {
                actionMode = activity?.startActionMode(actionModeCallback)
            }
        }

        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        noteAdapter = NoteListAdapter({
            val action = HomeFragmentDirections.actionHomeFragmentToCreateNoteFragment(
                id = it.id)
            findNavController().navigate(action)
        }, { note, position ->

            if (actionMode == null) {
                actionMode = activity?.startActionMode(actionModeCallback)
            }
            if (homeFragmentViewModel.selectedPosition.contains(position)) {
                homeFragmentViewModel.removeFromDelete(note, position)
                val view = recyclerView.layoutManager?.findViewByPosition(position)
                view?.setBackgroundColor(Color.parseColor("#2B3131"))
            } else {
                homeFragmentViewModel.addToDelete(note, position)
                val view = recyclerView.layoutManager?.findViewByPosition(position)
                view?.setBackgroundColor(Color.parseColor("#887B06"))
            }
            if (homeFragmentViewModel.selectedPosition.isEmpty()) {
                actionMode?.finish()
                noteAdapter.isSelected = false
            } else {

                noteAdapter.isSelected = true
            }
            actionMode?.invalidate()
        })
        recyclerView.adapter = noteAdapter
        lifecycle.coroutineScope.launch {
            homeFragmentViewModel.getAllNotes().collect {
                noteAdapter.submitList(it)
                searchNotes.clear()
                searchNotes.addAll(it)
            }
        }
        binding.createNewNoteButton.setOnClickListener {
            actionMode?.finish()
            findNavController().navigate(R.id.action_homeFragment_to_createNoteFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        recyclerView.viewTreeObserver.addOnGlobalLayoutListener {
            for (pos in homeFragmentViewModel.selectedPosition.keys) {
                val view = recyclerView.layoutManager?.findViewByPosition(pos)
                view?.setBackgroundColor(Color.parseColor("#887B06"))
                Log.d("Wagle", "Your View is $view")
            }
        }
    }


    private val actionModeCallback = object : ActionMode.Callback {
        // Called when the action mode is created; startActionMode() was called
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            // Inflate a menu resource providing context menu items
            val inflater: MenuInflater = mode.menuInflater
            inflater.inflate(R.menu.context_menu, menu)
            return true
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            val menuItem = menu.findItem(R.id.selected_size)
            if (homeFragmentViewModel.selectedPosition.isEmpty()) {
                menuItem.title = "0"
            } else {
                menuItem.title = homeFragmentViewModel.selectedPosition.size.toString()
            }
            return true
        }

        // Called when the user selects a contextual menu item
        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.delete -> {
                    for (note in homeFragmentViewModel.selectedPosition.values) {
                        homeFragmentViewModel.deleteNote(note)
                        homeFragmentViewModel.insertTrashNote(note)
                    }
                    homeFragmentViewModel.selectedPosition.clear()
                    noteAdapter.isSelected = false
                    mode.finish() // Action picked, so close the CAB
                    true
                }
                else -> false
            }
        }


        // Called when the user exits the action mode
        override fun onDestroyActionMode(mode: ActionMode) {
            actionMode = null
            noteAdapter.isSelected = false
            unselectAllNotes(homeFragmentViewModel.selectedPosition.keys)
            homeFragmentViewModel.selectedPosition.clear()

        }
    }

    fun unselectAllNotes(keys: MutableSet<Int>) {
        for (pos in keys) {
            val view = recyclerView.layoutManager?.findViewByPosition(pos)
            view?.setBackgroundColor(Color.parseColor("#2B3131"))
            Log.d("Wagle", "Unselect View is $view")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        val searchItem = menu.findItem(R.id.search_view)
        val searchView = searchItem?.actionView as SearchView
        searchView.setBackgroundColor(Color.WHITE)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val newNotes = ArrayList<Note>()
                for (note in searchNotes) {
                    if (note.noteTitle.lowercase(Locale.getDefault())
                            .contains(newText.toString().lowercase(Locale.getDefault()))
                        ||
                        note.noteDescription.lowercase(Locale.getDefault())
                            .contains(newText.toString().lowercase(Locale.getDefault()))
                    ) {
                        newNotes.add(note)
                    }
                }
                noteAdapter.submitList(newNotes)
                return true
            }
        })
    }


}


