package com.example.carapihelloworld

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carapihelloworld.data.Fault

class FaultAdapter(private val mList: List<Fault>) : RecyclerView.Adapter<FaultAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val severityImage: ImageView = itemView.findViewById(R.id.severity_icon)
        val faultDesc: TextView = itemView.findViewById(R.id.fault_description)
        val fCAP: TextView = itemView.findViewById(R.id.fCap)
        val status: TextView = itemView.findViewById(R.id.status)
        val count: TextView = itemView.findViewById(R.id.count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fault_cell, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fault = mList[position]
        holder.severityImage.setImageResource(fault.image)
        holder.faultDesc.text = fault.text
        holder.fCAP.text = fault.fCAP
        holder.status.text = fault.status
        holder.count.text = "count: ${fault.count}"
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}