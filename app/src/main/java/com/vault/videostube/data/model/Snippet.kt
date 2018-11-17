package com.vault.videostube.data.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity

@Entity(tableName = "snippet")

data class Snippet (var videoId: String, var title: String, @Embedded var thumbnails: Thumbnails) {

    override fun toString(): String {
        return "snippet title = $title, thumbmails = $thumbnails"
    }
}