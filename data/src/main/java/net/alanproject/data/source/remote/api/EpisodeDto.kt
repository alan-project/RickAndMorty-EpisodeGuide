package net.alanproject.data.source.remote.api

import androidx.annotation.Keep
import net.alanproject.domain.model.DomainEpiModel

@Keep
data class EpisodeDto(
    val id: Long = 0,
    val name: String = "unknown",
    val air_date: String = "",
    val episode: String = "",
    val characters: List<String> = listOf(),
    val url: String = "",
    val created: String = ""
)


fun EpisodeDto.mapToDomainEpiModel():DomainEpiModel = DomainEpiModel(
    id = id,
    name = name,
    air_date = air_date,
    episode = episode,
    characters = characters.map{
        net.alanproject.domain.model.Character(it)
    },
    url = url,
    created = created
)
