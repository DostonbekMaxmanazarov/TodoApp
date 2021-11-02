package com.example.todoapp.ui.adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.source.room.entity.GroupData
import com.example.todoapp.utils.SingleBlock
import com.example.todoapp.utils.extensions.bindItem
import com.example.todoapp.utils.extensions.inflate
import kotlinx.android.synthetic.main.item_group.view.*

class GroupAdapter : RecyclerView.Adapter<GroupAdapter.VH>() {

    private val ls = ArrayList<GroupData>()
    private var listenerItem: SingleBlock<GroupData>? = null
    private var listenerEdit: SingleBlock<GroupData>? = null
    private var listenerDelete: SingleBlock<GroupData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(parent.inflate(R.layout.item_group))

    override fun getItemCount(): Int = ls.size

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()

    fun submitList(data: List<GroupData>) {
        ls.clear()
        ls.addAll(data)
        notifyItemRangeRemoved(0, data.size)
    }

    fun removeItem(data: GroupData) {
        val index = ls.indexOfFirst {
            it.id == data.id
        }
        ls.removeAt(index)
        notifyItemRemoved(index)
    }

    fun updateItem(data: GroupData) {
        val index = ls.indexOfFirst {
            it.id == data.id
        }
        ls[index] = data
        notifyItemChanged(index)
    }

    fun insertItem(data: GroupData) {
        ls.add(data)
        notifyItemInserted(ls.size - 1)
    }

    fun setOnItemClickListener(block: SingleBlock<GroupData>) {
        listenerItem = block
    }

    fun setOnItemEditListener(block: SingleBlock<GroupData>) {
        listenerEdit = block
    }

    fun setOnItemDeleteListener(block: SingleBlock<GroupData>) {
        listenerDelete = block
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.apply {
                setOnClickListener { listenerItem?.invoke(ls[adapterPosition]) }
                buttonMore.setOnClickListener { it ->
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
            textTitle.text = d.name
            if (d.degreeColor.isNotEmpty())
            groupColor.setBackgroundColor(Color.parseColor(d.degreeColor))
        }
    }
}