package com.vault.videostube.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

data class Thumbnail(var width: String, var height: String, var url: String) {

    override fun toString() = "width = $width, height = $height, url = $url"
}