package com.example.todoapp.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.source.room.entity.CommentData
import com.example.todoapp.utils.SingleBlock
import com.example.todoapp.utils.extensions.bindItem
import com.example.todoapp.utils.extensions.inflate
import kotlinx.android.synthetic.main.item_student.view.*

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.VH>() {
    private val ls = ArrayList<CommentData>()

    private var listenerItem: SingleBlock<CommentData>? = null
    private var listenerEdit: SingleBlock<CommentData>? = null
    private var listenerDelete: SingleBlock<CommentData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(parent.inflate(R.layout.item_student))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()

    override fun getItemCount(): Int = ls.size

    fun submitList(data: List<CommentData>) {
        ls.clear()
        ls.addAll(data)
        notifyItemRangeRemoved(0, data.size)
    }

    fun removeItem(data: CommentData) {
        val index = ls.indexOfFirst {
            it.id == data.id
        }
        ls.removeAt(index)
        notifyItemRemoved(index)
    }

    fun updateItem(data: CommentData) {
        val index = ls.indexOfFirst {
            it.id == data.id
        }
        ls[index] = data
        notifyItemChanged(index)
    }

    fun insertItem(data: CommentData) {
        ls.add(data)
        notifyItemInserted(ls.size - 1)
    }

    fun setOnItemEditListener(block: SingleBlock<CommentData>) {
        listenerEdit = block
    }

    fun setOnItemDeleteListener(block: SingleBlock<CommentData>) {
        listenerDelete = block
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.apply {
                setOnClickListener { listenerItem?.invoke(ls[adapterPosition]) }

                ivbt.setOnClickListener {
                    val menu = PopupMenu(context, it)
                    menu.inflate(R.menu.menu_more)
                    menu.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.menuDelete -> listenerDelete?.invoke(ls[adapterPosition])
                            R.id.menuEdit -> listenerEdit?.invoke(ls[adapterPosition])
                        }
                        true
                    }
                    menu.show()
                }
            }
        }

        fun bind() = bindItem {

            val d = ls[adapterPosition]
            tvtitle.text = d.title
            tvGroup.text=d.group

        }

    }
}