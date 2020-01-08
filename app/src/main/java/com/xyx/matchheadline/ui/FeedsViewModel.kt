package com.xyx.matchheadline.ui

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.xyx.matchheadline.api.FeedApi
import com.xyx.matchheadline.ui.result.ResultFragmentDirections
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

    fun onSkipClick() {
        index.value = (index.value ?: 0) + 1
    }

    fun calculateProgress(index: Int, total: Int): Int {
        return if (total != 0) {
            (index * 1.0 / total * 100).toInt()
        } else {
            0
        }
    }

}