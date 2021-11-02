package com.example.todoapp.ui.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.data.source.room.AppDatabase
import com.example.todoapp.ui.adapter.CommentAdapter
import com.example.todoapp.ui.dialog.CommentDialog
import kotlinx.android.synthetic.main.activity_comment.*

class CommentActivity : AppCompatActivity() {

    private val adapter = CommentAdapter()
    private val db by lazy { AppDatabase.getDatabas(this) }
    private val commentDao by lazy { db.commentDao() }
    private val groupId by lazy { intent.extras?.getLong("GroupId") ?: 0 }
    private val groupColor by lazy { intent.extras?.getString("GroupColor") ?: "" }
    private val groupName by lazy { intent.extras?.getString("GroupName") ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setUpRvAndAdapter()

        /*comment_color.setBackgroundColor(Color.parseColor(groupColor))
        comment_text.text = groupName*/
    }

    private fun setUpRvAndAdapter() {
        listComment.layoutManager = LinearLayoutManager(this)
        listComment.adapter = adapter

        adapter.submitList(commentDao.getCommentsByGroupId(groupId))
        adapter.setOnItemDeleteListener {
            commentDao.delete(it)
            adapter.removeItem(it)
        }

        adapter.setOnItemEditListener {
            val dialog = CommentDialog(this, "Edit")
            dialog.setCommentData(it)
            dialog.setOnClickListener {
                commentDao.update(it)
                adapter.updateItem(it)
            }
            dialog.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menuAdd -> {
                val dialog = CommentDialog(this, "Add")
                dialog.setOnClickListener {
                    it.groupId = groupId
                    val id = commentDao.insert(it)
                    it.id = id
                    adapter.insertItem(it)
                }
                dialog.show()
            }
        }
        return true
    }

}