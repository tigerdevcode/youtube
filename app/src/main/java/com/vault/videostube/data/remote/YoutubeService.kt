package com.vault.videostube.data.remote

import com.vault.videostube.BuildConfig
import com.vault.videostube.data.model.PopularVideosResponse
import com.vault.videostube.data.model.SearchVideosResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface YoutubeService {

    @GET("videos")
    fun getMostPopularVideos(@Query("key") key: String = BuildConfig.API_KEY,
                             @Query("part") part: String = "snippet,contentDetails,statistics",
                             @Query("chart") id: String = "mostPopular",
                             @Query("regionCode") regionCode: String = Locale.getDefault().country,
                             @Query("maxResults") maxResults: Int = 15,
                             @Query("pageToken") nextPageToken: String = "",
                             @Query("videoCategoryId") categoryId: String = ""): Flowable<PopularVideosResponse>


    @GET("search")
    fun search(@Query("q") q: String,
               @Query("key") key: String = BuildConfig.API_KEY,
               @Query("maxResults") maxResults: Int = 7,
               @Query("pageToken") nextPageToken: String = "",
               @Query("part") part: String = "snippet") : Flowable<SearchVideosResponse>

}