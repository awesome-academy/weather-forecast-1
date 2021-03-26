package com.li.weatherapp.ui.news

import com.li.weatherapp.R
import com.li.weatherapp.base.BaseFragment
import com.li.weatherapp.data.model.News
import com.li.weatherapp.data.repository.NewsRepository
import com.li.weatherapp.data.source.remote.NewsRemoteDataSource
import com.li.weatherapp.ui.adapters.NewsAdapter
import com.li.weatherapp.utils.showToast
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : BaseFragment(), NewsContract.View {

    private var presenter: NewsContract.Presenter? = null
    private val adapter = NewsAdapter(this::newsItemClick)

    override val layoutResource: Int = R.layout.fragment_news

    override fun setupViews() {
        setupAdapter()
    }

    override fun setupData() {
        presenter =
            NewsPresenter(this, NewsRepository.getInstance(NewsRemoteDataSource.getInstance()))
        presenter?.start()
    }

    override fun initActions() {
        swipeRefreshNews.setOnRefreshListener {
            swipeRefreshNews.isRefreshing = false
            presenter?.getNews()
        }
    }

    override fun showNews(newsList: List<News>) {
        adapter.updateData(newsList)
    }

    override fun showMessage(data: Any) {
        context?.showToast(data.toString())
    }

    private fun setupAdapter() {
        recyclerViewNews.adapter = adapter
    }

    private fun newsItemClick(item: News) {
        context?.let {
            startActivity(NewsActivity.getIntent(it, item.link, item.title))
        }
    }
}
