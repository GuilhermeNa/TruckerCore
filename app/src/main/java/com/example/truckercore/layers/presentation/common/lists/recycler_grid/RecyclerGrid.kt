package com.example.truckercore.layers.presentation.common.lists.recycler_grid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.truckercore.R

class RecyclerGrid(private val dataSet: Array<GridItem>) :
    RecyclerView.Adapter<RecyclerGrid.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.list_item_grid_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_grid, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        holder.textView.text = item.text
        holder.textView.setCompoundDrawablesWithIntrinsicBounds(0, item.iconResource,0,0)
    }

}
