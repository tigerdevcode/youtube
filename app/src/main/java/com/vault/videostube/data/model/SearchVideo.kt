package com.vault.videostube.data.model

import com.google.gson.annotations.SerializedName

class SearchVideo(var kind: String,
                  var etag: String,
                  var id: SearchId,
                  var snippet: Snippet)

class SearchId (val kind: String,
                @SerializedName("videoId") val videoId: String)