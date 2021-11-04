package com.eurofins.mynotesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eurofins.mynotesapp.R
import com.eurofins.mynotesapp.database.Note

class NoteRVAdapter<T>(val onItemClicked: (T) -> Unit, val onItemLongClicked: (T, Int) -> Boolean):
    RecyclerView.Adapter<NoteRVAdapter.NoteRVViewHolder>() {

    val dataset = listOf<String>("Title1","Title2","Title3","Title4")
    val dataset1 = listOf<String>("Wagle\nWagle\nWagle\nWagle\n","Wagle\nWagle\nWagle\nWagle\nWagle\nWagle\n",
        "Wagle\nWagle\nWagle\nWagle\nWagle\nWagle\nWagle\nWagle\nWagle\nWagle\nWagle\nWagle\nWagle\nWagle\n","")

    class NoteRVViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val noteTitle: TextView = view.findViewById(R.id.note_title)
        val noteDescription: TextView = view.findViewById(R.id.note_description)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteRVViewHolder {
        return NoteRVViewHolder(LayoutInflater.from(parent.context).
        inflate(R.layout.notes_item, parent, false))
    }

    override fun onBindViewHolder(holder: NoteRVViewHolder, position: Int) {
        holder.noteTitle.text = dataset[position]
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}