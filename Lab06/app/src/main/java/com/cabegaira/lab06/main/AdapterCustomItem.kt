package com.cabegaira.lab06.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cabegaira.lab06.R
import java.util.*
import kotlin.collections.ArrayList

class RecyclerView_Adapter_Item(private var items: ArrayList<Items>): RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    Filterable {

    var itemsList: ArrayList<Items>? = null

    lateinit var mcontext: Context

    class PersonHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        this.itemsList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val personListView = LayoutInflater.from(parent.context).inflate(R.layout., parent, false)
        val sch = PersonHolder(personListView)
        mcontext = parent.context
        return sch
    }

    override fun getItemCount(): Int {
        return itemsList?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList?.get(position)
        holder.itemView.findViewById<TextView>(R.id.tvName)?.text = item?.desc
        holder.itemView.findViewById<TextView>(R.id.tvId)?.text = item?.id.toString()
        holder.itemView.findViewById<ImageView>(R.id.ivFoto).setImageBitmap(item?.Img_item)
/*
        holder.itemView.setOnClickListener {
            val intent = Intent(this.mcontext, GetMatricula::class.java)
            intent.putExtra("student", item?.id)
            this.mcontext.startActivity(intent)
        }*/

    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    itemsList = items
                } else {
                    val resultList = ArrayList<Items>()
                    for (row in items) {
                        if (row.id.toString().toLowerCase().contains(charSearch.toLowerCase())) {
                            resultList.add(row)
                        }
                        if (row.desc.toLowerCase().contains(charSearch.toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    itemsList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = itemsList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemsList = results?.values as ArrayList<Items>
                notifyDataSetChanged()
            }

        }
    }
}

