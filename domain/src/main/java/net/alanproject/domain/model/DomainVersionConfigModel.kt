package net.alanproject.domain.model

import com.google.gson.annotations.SerializedName

data class DomainVersionConfigModel(
    @SerializedName("latest_version")
    var latestVersion:Int=0,
    @SerializedName("force_update")
    var forceUpdate:Boolean = false,
    @SerializedName("force_update_target")
    var forceUpdateTarget:List<Int> = listOf()
)
