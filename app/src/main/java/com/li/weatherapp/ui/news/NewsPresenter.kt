package com.li.weatherapp.ui.news

import com.li.weatherapp.data.model.News
import com.li.weatherapp.data.repository.NewsRepository
import com.li.weatherapp.data.source.utils.OnDataLoadCallback
import java.lang.Exception

class NewsPresenter(
    private val view: NewsContract.View,
    private val newsRepository: NewsRepository
) : NewsContract.Presenter {

    override fun getNews() {
        newsRepository.getNews(object : OnDataLoadCallback<List<News>> {
            override fun onSuccess(data: List<News>) {
                view.showNews(data)
            }

            override fun onFail(exception: Exception) {
                view.showMessage(exception.message.toString())
            }
        })
    }

    override fun start() {
        getNews()
    }
}
