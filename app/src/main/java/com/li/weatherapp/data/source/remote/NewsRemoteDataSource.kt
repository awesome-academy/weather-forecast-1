package com.li.weatherapp.data.source.remote

import com.li.weatherapp.BuildConfig
import com.li.weatherapp.data.model.News
import com.li.weatherapp.data.source.NewsDataSource
import com.li.weatherapp.data.source.remote.utils.RemoteAsyncTask
import com.li.weatherapp.data.source.remote.utils.makeNetworkCall
import com.li.weatherapp.data.source.utils.OnDataLoadCallback
import org.w3c.dom.Document
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.io.StringReader
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

@Suppress("DEPRECATION")
class NewsRemoteDataSource private constructor() : NewsDataSource {

    override fun getNews(callback: OnDataLoadCallback<List<News>>) {
        RemoteAsyncTask(callback) {
            getNews()
        }.execute()
    }

    private fun getDocumentFromXml(xml: String): Document? {
        val document: Document
        val factory = DocumentBuilderFactory.newInstance()
        try {
            val documentBuilder = factory.newDocumentBuilder()
            val inputSource = InputSource().apply {
                characterStream = StringReader(xml)
                encoding = ENCODING
            }
            document = documentBuilder.parse(inputSource)
        } catch (e: ParserConfigurationException) {
            if (BuildConfig.DEBUG) e.printStackTrace()
            return null
        }
        return document
    }

    private fun getNews(): List<News> {
        val newsList = mutableListOf<News>()
        val xmlString = makeNetworkCall(RSS)
        val document = getDocumentFromXml(xmlString)
        val nodeList = document?.getElementsByTagName(RSS_ITEM) as NodeList
        val nodeListTitle = document.getElementsByTagName(RSS_TITLE) as NodeList
        val nodeListLink = document.getElementsByTagName(RSS_LINK) as NodeList
        val nodeListDescription = document.getElementsByTagName(RSS_DESCRIPTION) as NodeList
        val pattern = Pattern.compile(RSS_IMG_FORMAT)
        var title: String
        var link: String
        var img: String? = null
        var description: String
        var matcher: Matcher

        for (index in 0 until nodeList.length) {
            title = nodeListTitle.item(index + 1).textContent.toString()
            link = nodeListLink.item(index + 1).textContent.toString()
            val elementDescription = nodeListDescription.item(index).textContent
            val output = elementDescription.split(RSS_BR)
            description = output[1]
            matcher = pattern.matcher(elementDescription)
            if (matcher.find()) img = matcher.group(1)
            newsList.add(News(title, link, description, img))
        }
        return newsList
    }

    companion object {
        private const val ENCODING = "UTF-16LE"
        private const val RSS = "https://vtv.vn/du-bao-thoi-tiet.rss"
        private const val RSS_ITEM = "item"
        private const val RSS_TITLE = "title"
        private const val RSS_LINK = "link"
        private const val RSS_DESCRIPTION = "description"
        private const val RSS_BR = "</a>"
        private const val RSS_IMG_FORMAT = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>"
        private var instance: NewsRemoteDataSource? = null

        fun getInstance() = instance ?: NewsRemoteDataSource().also { instance = it }
    }
}
