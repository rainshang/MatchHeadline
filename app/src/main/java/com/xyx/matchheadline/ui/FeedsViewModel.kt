package com.xyx.matchheadline.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xyx.matchheadline.api.FeedApi
import com.xyx.matchheadline.vo.FeedsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedsViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _feedsResp = MutableLiveData<FeedsResponse>()
    val feedsResp: LiveData<FeedsResponse> = _feedsResp

    val score = MutableLiveData<Int>()
    val index = MutableLiveData<Int>()

    fun loadFeeds() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _feedsResp.postValue(FeedApi.instance.fetchFeeds())
            } catch (e: Exception) {
                System.err.println(e)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

}