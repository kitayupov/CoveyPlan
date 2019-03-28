package com.kamtayupov.koviplan.list.adapter

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseAdapter<T, VH : BaseAdapter.BaseViewHolder<T>> : RecyclerView.Adapter<VH>() {
    var list = ArrayList<T>()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
        open fun bind(item: T, callback: OnItemSelectedCallback<T>) {
            itemView.setOnClickListener { callback.onSelected(item) }
        }
    }

    interface OnItemSelectedCallback<T> {
        fun onSelected(item: T)
    }
}
