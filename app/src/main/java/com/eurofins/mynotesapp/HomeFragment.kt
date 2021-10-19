package com.eurofins.mynotesapp

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.eurofins.mynotesapp.adapter.NoteListAdapter
import com.eurofins.mynotesapp.adapter.NoteRVAdapter
import com.eurofins.mynotesapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {


    private lateinit var homeFragmentViewModel: NoteViewModel
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val notesDao = NoteDatabase.getDatabase(application).getNotesDao()

        val viewModelFactory = NoteViewModelFactory(notesDao, application)

        homeFragmentViewModel = ViewModelProvider(this,
            viewModelFactory).get(NoteViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val noteAdapter = NoteListAdapter({})
        recyclerView.adapter = noteAdapter

        lifecycle.coroutineScope.launch{
            homeFragmentViewModel.getAllNotes().collect {
                noteAdapter.submitList(it)
            }
        }



        binding.createNewNoteButton.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_createNoteFragment)
        }
    }
}