package com.eurofins.mynotesapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eurofins.mynotesapp.database.TrashNote
import com.eurofins.mynotesapp.databinding.NotesItemBinding

class TrashNoteListAdapter: ListAdapter<TrashNote, TrashNoteListAdapter.TrashNoteViewHolder>(DiffCallBack) {

    companion object{
        val DiffCallBack = object: DiffUtil.ItemCallback<TrashNote>() {
        override fun areItemsTheSame(oldItem: TrashNote, newItem: TrashNote): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: TrashNote, newItem: TrashNote): Boolean {
            return oldItem == newItem
        }

    }
    }

    class TrashNoteViewHolder(var binding: NotesItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(trashNote: TrashNote) {
            binding.noteTitle.text = trashNote.noteTitle
            binding.noteDescription.text = trashNote.noteDescription
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrashNoteViewHolder {
        val viewHolder = TrashNoteViewHolder(NotesItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))

        return viewHolder
    }

    override fun onBindViewHolder(holder: TrashNoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}