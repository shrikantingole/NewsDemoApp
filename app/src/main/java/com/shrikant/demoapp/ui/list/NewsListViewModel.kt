package com.shrikant.demoapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shrikant.demoapp.basic.App
import com.shrikant.demoapp.basic.isConnectedToNetwork
import com.shrikant.domain.news.Article
import com.shrikant.domain.news.NewsRes
import com.shrikant.network.extension.Event
import com.shrikant.network.extension.Results
import com.shrikant.network.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsListViewModel : ViewModel() {

    var text: String = ""
    var isLoading: Boolean = false
    var isLastPage: Boolean = false
    var page: Int = 1


    private val _loading = MutableLiveData<Event<Boolean>>()
    val loading: LiveData<Event<Boolean>> = _loading

    private val _newsListObserver = MutableLiveData<Event<List<Article>>>()
    val newsListObserver: LiveData<Event<List<Article>>> = _newsListObserver

    private val _failureObserver = MutableLiveData<Event<String>>()
    val failureObserver: LiveData<Event<String>> = _failureObserver

    //api call using coroutine with retrofit
    fun callNewsList(isNetworkCall: Boolean) {
        if (isNetworkCall && !App.instance.isConnectedToNetwork())
            callOfflineList()
        _loading.value = Event(true)
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = NewsRepository.callNewsListAsync(page)) {
                is Results.Success -> handleSuccess(result.data)
                is Results.Error -> handleFailure(result.exception)
            }
            _loading.postValue(Event(false))
        }
    }

    //handle the failur of network call
    private fun handleFailure(exception: Exception) {
        _failureObserver.postValue(
            Event(
                exception.message ?: ""
            )
        )
        isLoading = false
    }

    fun callOfflineList() {
        _loading.value = Event(true)
        viewModelScope.launch(Dispatchers.IO) {
            _newsListObserver.postValue(Event(NewsRepository.callOfflineNews(App.instance)))
            _loading.postValue(Event(false))
        }
    }

    private suspend fun handleSuccess(newsRes: NewsRes) {
        _newsListObserver.postValue(
            Event(
                NewsRepository.insertNews(
                    newsRes.articles,
                    App.instance
                    , page == 1
                )
            )
        )
//        _newsListObserver.postValue(Event(newsRes.articles))
        isLastPage = newsRes.totalResults < page
        isLoading = false
    }


}
