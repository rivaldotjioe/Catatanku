package com.rivaldo.catatanku

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rivaldo.catatanku.catatan.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter : androidx.recyclerview.widget.ListAdapter<Note, NoteAdapter.NoteHolder>(DIFF_CALLBACK) {
    companion object {
        //        Logic untuk mengurutkan prioritas
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.title == newItem.title && oldItem.description == newItem.description
                        && oldItem.priority == newItem.priority
            }
        }
    }

    //    menetapkan note_item sebagai layout RecyclerView
    private var listener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteHolder(itemView)
    }
    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote: Note = getItem(position)
        holder.textViewTitle.text = currentNote.title
        holder.textViewPriority.text = currentNote.priority.toString()
        holder.textViewDescription.text = currentNote.description
    }

    //    get position item yang dipilih
    fun getNoteAt(position: Int): Note {
        return getItem(position)
    }

    //    set data pada item RecyclerView
    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }
        var textViewTitle: TextView = itemView.text_view_title
        var textViewPriority: TextView = itemView.text_view_priority
        var textViewDescription: TextView = itemView.text_view_description
    }

    //    Menambahkan clickListener pada app
    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}
