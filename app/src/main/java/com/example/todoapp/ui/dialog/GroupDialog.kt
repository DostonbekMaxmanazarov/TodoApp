package com.example.todoapp.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.todoapp.R
import com.example.todoapp.data.model.Degree
import com.example.todoapp.data.source.room.entity.GroupData
import com.example.todoapp.ui.adapter.SpinnerAdap
import com.example.todoapp.utils.SingleBlock
import kotlinx.android.synthetic.main.dialog_group.view.*

class GroupDialog(context: Context, actionName: String, val degree: ArrayList<Degree>?) :
    AlertDialog(context) {

    private var adapter: SpinnerAdap? = null

    private val contentView =
        LayoutInflater.from(context).inflate(R.layout.dialog_group, null, false)
    private var listener: SingleBlock<GroupData>? = null
    private var groupData: GroupData? = null

    init {
        adapter = SpinnerAdap(degree!!)
        contentView.spinner.adapter = adapter
        setView(contentView)
        setButton(BUTTON_POSITIVE, actionName) { _, _ ->
            val data = groupData ?: GroupData()
            data.name = contentView.inputName.text.toString()
            data.degreeColor = degree[contentView.spinner.selectedItemPosition].color
            data.degreeName = degree[contentView.spinner.selectedItemPosition].name
            listener?.invoke(data)
        }
        setButton(BUTTON_NEGATIVE, "Cancel") { _, _ -> }
    }

    fun setGroupData(groupData: GroupData) = with(contentView) {
        this@GroupDialog.groupData = groupData
        inputName.setText(groupData.name)
    }

    fun setOnClickListener(block: SingleBlock<GroupData>) {
        listener = block
    }

}