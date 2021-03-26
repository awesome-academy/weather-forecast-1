package com.li.weatherapp.data.repository

import com.li.weatherapp.data.model.News
import com.li.weatherapp.data.source.NewsDataSource
import com.li.weatherapp.data.source.utils.OnDataLoadCallback

class NewsRepository private constructor(private val remote: NewsDataSource) : NewsDataSource {

    override fun getNews(callback: OnDataLoadCallback<List<News>>) {
        remote.getNews(callback)
    }

    companion object {
        private var instance: NewsRepository? = null
        fun getInstance(remote: NewsDataSource) =
            instance ?: NewsRepository(remote).also { instance = it }
    }
}
