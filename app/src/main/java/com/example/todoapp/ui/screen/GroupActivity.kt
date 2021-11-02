package com.example.todoapp.ui.screen

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.data.model.Degree
import com.example.todoapp.data.source.room.AppDatabase
import com.example.todoapp.ui.adapter.GroupAdapter
import com.example.todoapp.ui.dialog.GroupDialog
import kotlinx.android.synthetic.main.activity_group.*

class GroupActivity : AppCompatActivity() {

    private val adapter = GroupAdapter()
    private val db by lazy { AppDatabase.getDatabas(this) }
    private val groupDao by lazy { db.groupDao() }
    private val commentDao by lazy { db.commentDao() }
    private var degrees: ArrayList<Degree>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)
        setUpRvAndAdapter()
        loadDegree()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpRvAndAdapter() {
        listGroup.layoutManager = LinearLayoutManager(this)
        listGroup.adapter = adapter

        adapter.submitList(groupDao.getAll())

        adapter.setOnItemClickListener {


            startActivity(Intent(
                this,
                CommentActivity::class.java
            )
                .apply {
                    putExtra("GroupId", it.id)
                    putExtra("GroupColor", it.degreeColor)
                    putExtra("GroupName", it.degreeName)
                })

        }

        adapter.setOnItemDeleteListener {
            groupDao.delete(it)
            commentDao.deleteAllByGroup(it.id)
            adapter.removeItem(it)
        }

        adapter.setOnItemEditListener {
            val dialog = GroupDialog(this, "Edit", degrees)
            dialog.setGroupData(it)
            dialog.setOnClickListener {
                groupDao.update(it)
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
                val dialog = GroupDialog(this, "Add", degrees)
                dialog.setOnClickListener {
                    val id = groupDao.insert(it)
                    it.id = id
                    adapter.insertItem(it)
                }
                dialog.show()
            }
        }
        return true
    }

   private fun loadDegree() {
        degrees = ArrayList()
        degrees!!.add(Degree("Muhim", "#FF690B0B"))
        degrees!!.add(Degree("Normal", "#FF0B237C"))
        degrees!!.add(Degree("Odatiy", "#167C0B"))
    }
}