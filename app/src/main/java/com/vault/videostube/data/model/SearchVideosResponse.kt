package com.vault.videostube.data.model

import com.google.gson.annotations.SerializedName

class SearchVideosResponse  (var kind: String,
                             var etag: String,
                             @SerializedName("nextPageToken") var nextPageToken: String,
                             var items: List<SearchVideo>): BaseResponse()