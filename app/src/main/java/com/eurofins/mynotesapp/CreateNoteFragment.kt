package com.eurofins.mynotesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.eurofins.mynotesapp.databinding.FragmentCreateNoteBinding

class CreateNoteFragment : Fragment() {

    private lateinit var binding: FragmentCreateNoteBinding

    private lateinit var createNoteFragmentViewModel: NoteViewModel

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
        val application = requireNotNull(this.activity).application
        val notesDao = NoteDatabase.getDatabase(application).getNotesDao()
        val viewModelFactory = NoteViewModelFactory(notesDao, application)
        createNoteFragmentViewModel = ViewModelProvider(this,
            viewModelFactory).get(NoteViewModel::class.java)
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
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_createNoteFragment_to_homeFragment)
        }
    }
}