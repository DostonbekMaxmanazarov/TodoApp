package com.example.todoapp.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.todoapp.R
import com.example.todoapp.data.source.room.entity.CommentData
import com.example.todoapp.utils.SingleBlock
import kotlinx.android.synthetic.main.dialog_student.view.*
import kotlinx.android.synthetic.main.item_student.*

class CommentDialog(context: Context, actionName: String) : AlertDialog(context) {

    private val contentView =
        LayoutInflater.from(context).inflate(R.layout.dialog_student, null, false)

    private var listener: SingleBlock<CommentData>? = null
    private var commentData: CommentData? = null

    init {
        setView(contentView)
        setButton(BUTTON_POSITIVE, actionName) { _, _ ->
            val data = commentData ?: CommentData()
            data.title = contentView.inputTitle.text.toString()
            data.group = contentView.tetGroup.text.toString()

            listener?.invoke(data)
        }
        setButton(BUTTON_NEGATIVE, "Cancel") { _, _ -> }
    }

    fun setCommentData(commentData: CommentData) = with(contentView) {
        this@CommentDialog.commentData = commentData
        inputTitle.setText(commentData.title)
    }

    fun setOnClickListener(block: SingleBlock<CommentData>) {
        listener = block
    }

}