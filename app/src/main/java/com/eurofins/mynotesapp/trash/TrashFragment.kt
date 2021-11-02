package com.eurofins.mynotesapp

import android.os.Bundle
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.eurofins.mynotesapp.adapter.TrashNoteListAdapter
import com.eurofins.mynotesapp.databinding.FragmentTrashBinding
import com.eurofins.mynotesapp.trash.TrashViewModel
import com.eurofins.mynotesapp.trash.TrashViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class TrashFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
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
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTrashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        if (trashFragmentViewModel.selectedPosition.isNotEmpty()) {
//            if (actionMode == null) {
//                actionMode = activity?.startActionMode(actionModeCallback)
//            }
//        }

        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        trashNoteAdapter = TrashNoteListAdapter()
        recyclerView.adapter = trashNoteAdapter
        lifecycle.coroutineScope.launch {
            trashFragmentViewModel.getAllTrashNotes().collect{
                trashNoteAdapter.submitList(it)
            }
        }
    }

}