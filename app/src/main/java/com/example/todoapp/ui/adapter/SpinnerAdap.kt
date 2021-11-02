package com.example.todoapp.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.todoapp.R
import com.example.todoapp.data.model.Degree

class SpinnerAdap(val degrees: List<Degree>) : BaseAdapter() {
    override fun getCount() = degrees.size

    override fun getItem(position: Int) = degrees[position]

    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view =
            LayoutInflater.from(parent?.context)
                .inflate(
                    R.layout.item_spinner,
                    parent, false
                )
        view?.findViewById<LinearLayoutCompat>(R.id.spin_color)
            ?.setBackgroundColor(Color.parseColor(degrees[position].color))
        view?.findViewById<TextView>(R.id.spin_text)?.text = degrees[position].name
        return view
    }
}