package com.vault.videostube.data.local

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vault.videostube.data.model.Snippet


class VideoConverter {

    @TypeConverter
    fun toSnippet(json: String): Snippet {
        return Gson().fromJson(json, object : TypeToken<Snippet>(){}.type)
    }


    @TypeConverter
    fun toSnippetJson(snippet: Snippet): String {
        return Gson().toJson(snippet)
    }

}