package com.eurofins.mynotesapp

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.eurofins.mynotesapp.adapter.NoteListAdapter
import com.eurofins.mynotesapp.database.Note
import com.eurofins.mynotesapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private var searchNotes: ArrayList<Note> = ArrayList()
    private  var delNotes: ArrayList<Note> = ArrayList()
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
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val noteAdapter = NoteListAdapter ({
            val action = HomeFragmentDirections.actionHomeFragmentToCreateNoteFragment(
                id = it.id)
            findNavController().navigate(action)
        }, {
            if(delNotes.contains(it) ){
            delNotes.remove(it)
                Log.d("Wagle", "${delNotes}")
                return@NoteListAdapter false
            }else{
                delNotes.add(it)
                Log.d("Wagle", "ff${delNotes}")
                return@NoteListAdapter true
            }

        })
        recyclerView.adapter = noteAdapter

        lifecycle.coroutineScope.launch {
            homeFragmentViewModel.getAllNotes().collect {
                noteAdapter.submitList(it)
                searchNotes.clear()
                searchNotes.addAll(it)
            }
        }

        val searchView = binding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var newNotes = ArrayList<Note>()
                for (note in searchNotes) {
                    if (note.noteTitle.lowercase(Locale.getDefault())
                            .contains(newText.toString().lowercase(Locale.getDefault()))
                    ) {
                        newNotes.add(note)
                    }
                }
                noteAdapter.submitList(newNotes)
                return true
            }
        })

        binding.createNewNoteButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createNoteFragment)
        }
    }


    // Edit From Here
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("Wagle", " You are inside homefragments onOptionItemSelected")
    return  when (item.itemId) {
        R.id.delete -> {

            Log.d("Wagle","You pressed delete man")
            for (note in delNotes){
                homeFragmentViewModel.deleteNote(note)
            }
            delNotes.clear()
           true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
    }


}