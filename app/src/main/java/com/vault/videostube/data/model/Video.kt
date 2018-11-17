package com.vault.videostube.data.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.vault.videostube.data.local.VideoConverter

@Entity(tableName = "video_table")
data class Video(var kind: String,
                 var etag: String,
                 @PrimaryKey var id: String,
                 @TypeConverters(VideoConverter::class)
                 var snippet: Snippet) {

    override fun toString() = "kind = $kind, etaq = $etag, snippet = $snippet"

}