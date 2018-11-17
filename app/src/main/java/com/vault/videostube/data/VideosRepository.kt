package com.vault.videostube.data

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.util.Log
import com.vault.videostube.data.local.VideosDao
import com.vault.videostube.data.model.Resource
import com.vault.videostube.data.model.SearchVideo
import com.vault.videostube.data.model.SearchVideosResponse
import com.vault.videostube.data.model.Video
import com.vault.videostube.data.remote.YoutubeService
import com.vault.videostube.utils.BaseSchedulerProvider
import javax.inject.Inject

class VideosRepository @Inject constructor(private val youtubeService: YoutubeService,
                                           private val videosDao: VideosDao,
                                           private val schedulerProvider: BaseSchedulerProvider): VideosDataSource {

    companion object {
        private const val PAGE_SIZE = 5
        private const val ENABLE_PLACEHOLDERS = true
        private const val PAGE_PREFETCH_SIZE = 5
        private const val TAG = "VideosRepository"
    }

    private var nextPage: String = ""

    override fun getVideos(): LiveData<PagedList<Video>> {

        loadFromRemote(nextPage)
        val videos = LivePagedListBuilder(videosDao.getVideos(), PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setEnablePlaceholders(ENABLE_PLACEHOLDERS)
                .setPrefetchDistance(PAGE_PREFETCH_SIZE)
                .build())
                .setBoundaryCallback(object : PagedList.BoundaryCallback<Video>(){
                    override fun onZeroItemsLoaded() {
                        Log.e(TAG, "onZeroItemsLoaded")
                        super.onZeroItemsLoaded()
                    }

                    override fun onItemAtEndLoaded(itemAtEnd: Video) {
                        Log.e(TAG, "onItemAtEndLoaded + itemAtEnd ${itemAtEnd.snippet.title}")
                        super.onItemAtEndLoaded(itemAtEnd)
                        // when no item left load new portion from remote
                        loadFromRemote(nextPage)
                    }

                    override fun onItemAtFrontLoaded(itemAtFront: Video) {
                        Log.e(TAG, "onItemAtFrontLoaded + itemAtFront ${itemAtFront.snippet.title}")
                        super.onItemAtFrontLoaded(itemAtFront)
                    }
                })
                .build()
        return videos
    }

    fun loadFromRemote(page: String) {
        Log.e(TAG, "load from remote with page $page")
        youtubeService.getMostPopularVideos(nextPageToken = page)
                .subscribeOn(schedulerProvider.io())
                .subscribe ({
                    Log.e(TAG, "loaded from remote first item = ${it.items.first().snippet.title}, " +
                            "nextPage = ${it.nextPageToken}")
                    videosDao.addVideos(it.items)
                    nextPage = it.nextPageToken
                }, {
                    Log.e(TAG, "error while load from remote! ${it.message}")
                })
    }

    override fun searchVideos(query: String): LiveData<PagedList<SearchVideo>> {
        val searchDataSourceFactory = SearchDataSourceFactory(query, youtubeService, schedulerProvider)
        val results = LivePagedListBuilder(searchDataSourceFactory, PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setEnablePlaceholders(ENABLE_PLACEHOLDERS)
                .setPrefetchDistance(PAGE_PREFETCH_SIZE)
                .build())
                .setBoundaryCallback(object :PagedList.BoundaryCallback<SearchVideo>(){

                })
                .build()
        return results
    }

    override fun addVideo(video: Video) {
        schedulerProvider.io().scheduleDirect {
            videosDao.addVideo(video)
        }
    }

    override fun deleteVideo(title: String) {
        schedulerProvider.io().scheduleDirect {
            videosDao.deleteVideo(title)
        }
    }

}