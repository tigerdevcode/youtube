package com.vault.videostube.data.local

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.vault.videostube.data.model.Video

@Dao
interface VideosDao {

    //@Query("SELECT * FROM video_table")
    //fun getVideos(): LiveData<List<Video>>

    @Query("SELECT * FROM video_table")
    fun getVideos(): DataSource.Factory<Int, Video>

    @Query("DELETE FROM video_table")
    fun deleteVideos()

    @Query("DELETE FROM video_table WHERE id = :id")
    fun deleteVideo(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addVideo(video: Video)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addVideos(videos: List<Video>)

}