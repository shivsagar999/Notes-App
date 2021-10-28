//package com.eurofins.mynotesapp
//
//import android.graphics.Color
//import android.os.Bundle
//import android.view.ActionMode
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.activityViewModels
//import androidx.lifecycle.coroutineScope
//import androidx.lifecycle.lifecycleScope
//import androidx.navigation.fragment.findNavController
//import androidx.recyclerview.widget.RecyclerView
//import androidx.recyclerview.widget.StaggeredGridLayoutManager
//import com.eurofins.mynotesapp.adapter.NoteListAdapter
//import com.eurofins.mynotesapp.databinding.FragmentHomeBinding
//import com.eurofins.mynotesapp.databinding.FragmentTrashBinding
//import kotlinx.coroutines.flow.collect
//import kotlinx.coroutines.launch
//
//
//class TrashFragment : Fragment() {
//
//    lateinit var recyclerView: RecyclerView
//    lateinit var noteAdapter: NoteListAdapter
//
//    var actionMode: ActionMode? = null
//
//    private val trashFragmentViewModel: NoteViewModel by activityViewModels {
//        NoteViewModelFactory((activity?.application as NoteApplication).database.getNotesDao())
//    }
//
//    private lateinit var binding: FragmentTrashBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View? {
//        // Inflate the layout for this fragment
//        binding = FragmentTrashBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
////        if (trashFragmentViewModel.selectedPosition.isNotEmpty()) {
////            if (actionMode == null) {
////                actionMode = activity?.startActionMode(actionModeCallback)
////            }
////        }
//
//        recyclerView = binding.recyclerView
//        recyclerView.setHasFixedSize(true)
//        recyclerView.layoutManager =
//            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//        noteAdapter = NoteListAdapter({
////            val action = HomeFragmentDirections.actionHomeFragmentToCreateNoteFragment(
////                id = it.id)
////            findNavController().navigate(action)
////        }, { note, position ->
////
////            if (actionMode == null) {
////                actionMode = activity?.startActionMode(actionModeCallback)
////            }
////
////            if (homeFragmentViewModel.selectedPosition.contains(position)) {
////                homeFragmentViewModel.removeFromDelete(note, position)
////                val view = recyclerView.layoutManager?.findViewByPosition(position)
////                view?.setBackgroundColor(Color.parseColor("#2B3131"))
////            } else {
////                homeFragmentViewModel.addToDelete(note, position)
////                val view = recyclerView.layoutManager?.findViewByPosition(position)
////                view?.setBackgroundColor(Color.parseColor("#887B06"))
////            }
////
////            if (homeFragmentViewModel.selectedPosition.isEmpty()) {
////                actionMode?.finish()
////                noteAdapter.isSelected = false
////            } else {
////
////                noteAdapter.isSelected = true
////            }
////            actionMode?.invalidate()
//        },{_,_ ->})
//        recyclerView.adapter = noteAdapter
//        lifecycle.coroutineScope.launch {
//            trashFragmentViewModel.getAllDeletedNotes().collect{
//                noteAdapter.submitList(it)
//            }
//        }
//    }
//
//}