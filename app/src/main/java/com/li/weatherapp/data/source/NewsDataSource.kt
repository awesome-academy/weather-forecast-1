package com.li.weatherapp.data.source

import com.li.weatherapp.data.model.News
import com.li.weatherapp.data.source.utils.OnDataLoadCallback

interface NewsDataSource {
    fun getNews(callback: OnDataLoadCallback<List<News>>)
}
