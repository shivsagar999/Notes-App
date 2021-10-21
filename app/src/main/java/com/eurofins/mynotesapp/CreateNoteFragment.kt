package com.eurofins.mynotesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.eurofins.mynotesapp.databinding.FragmentCreateNoteBinding

class CreateNoteFragment : Fragment() {

    private lateinit var binding: FragmentCreateNoteBinding

    private val createNoteFragmentViewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory((activity?.application as NoteApplication).database.getNotesDao())
    }

    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt("id").toInt()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (id != 0) {
            createNoteFragmentViewModel.getNote(id!!)
            createNoteFragmentViewModel.note.observe(viewLifecycleOwner,
                Observer {
                    binding.title.setText(it.noteTitle)
                    binding.description.setText(it.noteDescription)
                })
        }

        binding.saveButton.setOnClickListener {
            if (id != 0) {
                createNoteFragmentViewModel.updateNote(binding.title.text.toString(),
                    binding.description.text.toString())
                findNavController().navigate(R.id.action_createNoteFragment_to_homeFragment)
            } else {
                if (binding.title.text.toString().isNotEmpty() or
                    binding.description.text.toString().isNotEmpty()
                ) {
                    val newNote = Note(noteTitle = binding.title.text.toString(),
                        noteDescription = binding.description.text.toString(),
                        timeStamp = "SSS")
                    createNoteFragmentViewModel.addNote(newNote)
                    findNavController().navigate(R.id.action_createNoteFragment_to_homeFragment)
                }
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_createNoteFragment_to_homeFragment)
        }
    }
}