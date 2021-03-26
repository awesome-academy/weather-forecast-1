package com.li.weatherapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.li.weatherapp.R
import com.li.weatherapp.base.BaseAdapter
import com.li.weatherapp.base.BaseViewHolder
import com.li.weatherapp.data.model.News

class NewsAdapter(private var itemClick: (News) -> Unit) : BaseAdapter<News>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<News> {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view, itemClick)
    }
}
