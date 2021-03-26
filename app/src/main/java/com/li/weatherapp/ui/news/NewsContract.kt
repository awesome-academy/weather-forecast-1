package com.li.weatherapp.ui.news

import com.li.weatherapp.base.BasePresenter
import com.li.weatherapp.base.BaseView
import com.li.weatherapp.data.model.News

interface NewsContract {
    interface View : BaseView {
        fun showNews(newsList: List<News>)
    }

    interface Presenter : BasePresenter {
        fun getNews()
    }
}
