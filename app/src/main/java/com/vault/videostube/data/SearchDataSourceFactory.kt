package com.vault.videostube.data

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.vault.videostube.data.model.SearchVideo
import com.vault.videostube.data.remote.YoutubeService
import com.vault.videostube.utils.BaseSchedulerProvider

class SearchDataSourceFactory(
        private val query: String,
        private val youtubeService: YoutubeService,
        private val schedulerProvider: BaseSchedulerProvider): DataSource.Factory<String, SearchVideo>() {

    private val sourceLiveData = MutableLiveData<SearchDataSource>()

    override fun create(): DataSource<String, SearchVideo> {

        val dataSource = SearchDataSource(query, youtubeService, schedulerProvider)
        sourceLiveData.postValue(dataSource)
        return dataSource
    }

}