package com.vault.videostube.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.vault.videostube.data.model.Snippet
import com.vault.videostube.data.model.Video

@Database(entities = [Video::class], version = 1, exportSchema = false)
@TypeConverters(VideoConverter::class)
abstract class VideosDatabase: RoomDatabase() {

    abstract fun videoDao(): VideosDao
}