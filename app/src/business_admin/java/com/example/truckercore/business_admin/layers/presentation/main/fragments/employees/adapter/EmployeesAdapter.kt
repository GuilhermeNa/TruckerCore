package com.example.truckercore.business_admin.layers.presentation.main.fragments.employees.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.truckercore.R
import com.example.truckercore.business_admin.layers.presentation.main.fragments.employees.content.EmployeesViewItem

class EmployeesAdapter(
    private val onItemClick: (EmployeesViewItem) -> Unit
) : RecyclerView.Adapter<EmployeesAdapter.ViewHolder>() {

    private val dataSet = mutableListOf<EmployeesViewItem>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.list_item_line_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_line, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]

        bindText(holder, item)
        bindImage(holder.textView, item.position)
        initClickListener(holder, item)

    }

    private fun initClickListener(holder: ViewHolder, item: EmployeesViewItem) {
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    private fun bindText(holder: ViewHolder, item: EmployeesViewItem) {
        holder.textView.text = item.name
    }

    private fun bindImage(view: TextView, position: String) {
        val resource = when (position) {
            "Admin" -> R.drawable.icon_computer
            "Motorista" -> R.drawable.icon_truck
            else -> 0
        }

        view.setCompoundDrawablesWithIntrinsicBounds(resource, 0, 0, 0)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(data: List<EmployeesViewItem>) {
        dataSet.clear()
        dataSet.addAll(data)
        notifyDataSetChanged()
    }

}