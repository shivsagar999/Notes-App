package com.eurofins.mynotesapp

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.eurofins.mynotesapp.adapter.NoteListAdapter
import com.eurofins.mynotesapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var arrNotes: List<Note>
    private val homeFragmentViewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory((activity?.application as NoteApplication).database.getNotesDao())
    }
    private lateinit var binding: FragmentHomeBinding
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
        val noteAdapter = NoteListAdapter {
            val action = HomeFragmentDirections.actionHomeFragmentToCreateNoteFragment(
                id = it.id)
            findNavController().navigate(action)
        }
        recyclerView.adapter = noteAdapter

        lifecycle.coroutineScope.launch {
            homeFragmentViewModel.getAllNotes().collect {
                noteAdapter.submitList(it)
                arrNotes = it

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
                for (note in arrNotes) {
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
}