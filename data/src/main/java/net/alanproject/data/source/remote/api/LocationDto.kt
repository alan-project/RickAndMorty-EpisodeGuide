package net.alanproject.data.source.remote.api

import androidx.annotation.Keep
import net.alanproject.domain.model.DomainLocationModel

@Keep
data class LocationDto(
    val results: List<LocationInfo> = listOf()
)

@Keep
data class LocationInfo(
    val id: Long = 0,
    val name: String = "",
    val type: String = "",
    val dimension: String = "",
    val residents: List<String> = listOf(),
    val url: String = "",
    val created: String = ""
)

fun LocationInfo.mapToDomain():DomainLocationModel = DomainLocationModel(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residents = residents,
    url = url,
    created = created
)