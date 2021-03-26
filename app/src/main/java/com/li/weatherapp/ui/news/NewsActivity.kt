package com.li.weatherapp.ui.news

import android.content.Context
import android.content.Intent
import android.webkit.WebViewClient
import com.li.weatherapp.R
import com.li.weatherapp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : BaseActivity() {

    override val layoutResource get() = R.layout.activity_news

    override fun initViews() {
        val link = intent?.getStringExtra(EXTRA_NEWS_LINK)
        val title = intent?.getStringExtra(EXTRA_NEWS_TITLE)
        webViewNews.webViewClient = WebViewClient()
        link?.let(webViewNews::loadUrl)
        title?.let {
            textTitleNews.text = title
        }

        buttonBackNews.setOnClickListener {
            finish()
        }
    }

    override fun initData() {
    }

    companion object {
        private const val EXTRA_NEWS_LINK = "EXTRA_NEWS_LINK"
        private const val EXTRA_NEWS_TITLE = "EXTRA_NEWS_TITLE"

        fun getIntent(context: Context, link: String, title: String): Intent =
            Intent(context, NewsActivity::class.java)
                .putExtra(EXTRA_NEWS_LINK, link)
                .putExtra(EXTRA_NEWS_TITLE, title)
    }
}
