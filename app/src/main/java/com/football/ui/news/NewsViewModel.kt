package com.football.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.football.core.Repository
import com.football.model.News
import com.football.utils.SingleLiveEvent
import java.lang.Exception
import kotlinx.coroutines.launch
import timber.log.Timber

class NewsViewModel(private val repository: Repository): ViewModel() {

    private val showLoadingSingleLiveEvent = SingleLiveEvent<Any>()
    val showLoadingObservable: LiveData<Any> = showLoadingSingleLiveEvent

    private val hideLoadingSingleLiveEvent = SingleLiveEvent<Any>()
    val hideLoadingObservable: LiveData<Any> = hideLoadingSingleLiveEvent

    private val requestErrorSingleLiveEvent = SingleLiveEvent<Any>()
    val requestErrorObservable: LiveData<Any> = requestErrorSingleLiveEvent

    private val newsResultLiveData = MutableLiveData<List<News>>()
    val newsResultObservable: LiveData<List<News>> = newsResultLiveData

    fun fetchNews() {
        viewModelScope.launch {
            try {
                showLoadingSingleLiveEvent.call()
                val news = repository.fetchNews()
                if (news.isNotEmpty()) {
                    newsResultLiveData.postValue(news)
                } else {
                    requestErrorSingleLiveEvent.call()
                }
            } catch (error: Exception) {
                Timber.w(error)
                requestErrorSingleLiveEvent.call()
            } finally {
                hideLoadingSingleLiveEvent.call()
            }
        }
    }
}