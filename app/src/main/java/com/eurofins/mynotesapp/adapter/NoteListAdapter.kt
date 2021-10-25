package com.eurofins.mynotesapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eurofins.mynotesapp.R
import com.eurofins.mynotesapp.database.Note
import com.eurofins.mynotesapp.databinding.NotesItemBinding

class NoteListAdapter(val onItemClicked: (Note) -> Unit, val onItemLongClicked: (Note, Int) -> Boolean) :
    ListAdapter<Note, NoteListAdapter.NoteViewHolder>(DiffCallback) {

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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val viewHolder = NoteViewHolder(NotesItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }

        viewHolder.itemView.setOnLongClickListener {
            val position = viewHolder.adapterPosition
            val background = onItemLongClicked(getItem(position), position)
            if (background) {
                viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(parent.context,
                    R.color.yellow))
            } else {
                viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(parent.context,
                    R.color.light_black))
            }
            true
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NoteListAdapter.NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }



}