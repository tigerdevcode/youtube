package com.vault.videostube.data.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

data class Thumbnails (@Embedded var default: Thumbnail?, @Embedded var medium: Thumbnail?,
                       @Embedded var high: Thumbnail?, @Embedded var maxres: Thumbnail?) {

    override fun toString() = "default $default, medium $medium, high $high, maxres $maxres"

}