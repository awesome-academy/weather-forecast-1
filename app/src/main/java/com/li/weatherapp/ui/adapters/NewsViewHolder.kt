package com.li.weatherapp.ui.adapters

import android.view.View
import com.li.weatherapp.base.BaseViewHolder
import com.li.weatherapp.data.model.News
import com.li.weatherapp.utils.load
import kotlinx.android.synthetic.main.item_news.view.*

class NewsViewHolder(
    private val view: View,
    private var itemClick: (News) -> Unit
) : BaseViewHolder<News>(view) {

    private var itemNews: News? = null

    init {
        view.setOnClickListener {
            itemNews?.let {
                itemClick(it)
            }
        }
    }

    override fun bindData(item: News) {
        itemNews = item
        view.run {
            textNewsTitle.text = item.title
            textNewsDescription.text = item.description
            item.image?.let {
                imageNews.load(it)
            }
        }
    }
}
