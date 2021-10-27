package com.eurofins.mynotesapp.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eurofins.mynotesapp.database.Note
import com.eurofins.mynotesapp.databinding.NotesItemBinding

class NoteListAdapter(val onItemClicked: (Note) -> Unit, val onItemSelected: (Note, Int) -> Unit) :
    ListAdapter<Note, NoteListAdapter.NoteViewHolder>(DiffCallback) {

    var isSelected: Boolean = false

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }
        }
    }

    class NoteViewHolder(private val binding: NotesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.noteTitle.text = note.noteTitle
            binding.noteDescription.text = note.noteDescription
            if(note.isSelected){
                itemView.setBackgroundColor(Color.parseColor("#887B06"))
            }else{
                itemView.setBackgroundColor(Color.parseColor("#2B3131"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val viewHolder = NoteViewHolder(NotesItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if(isSelected){
                onItemSelected(getItem(position), position)

            }else{
                onItemClicked(getItem(position))
            }
        }

        viewHolder.itemView.setOnLongClickListener {
            val position = viewHolder.adapterPosition
            Log.d("Wagle", "Position in adapter $position" )
            onItemSelected(getItem(position), position)
            true
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}