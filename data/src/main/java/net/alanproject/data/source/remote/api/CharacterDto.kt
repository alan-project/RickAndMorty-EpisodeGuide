package net.alanproject.data.source.remote.api

import androidx.annotation.Keep
import net.alanproject.domain.model.DomainCharacterModel

@Keep
data class CharacterDto(
    val results: List<Result> = listOf()
)

@Keep
data class Result(
    val id: Long = 0,
    val name: String = "unknown",
    val status: String = "unknown",
    val species: String = "unknown",
    val gender: String = "unknown",
    val origin: Origin = Origin(),
    val location: Location = Location(),
    val image: String = "",
    val episode: List<String> = listOf(),
    val url: String = "",
    val created: String = ""
)

@Keep
data class Origin(
    val name: String = "unknown",
    val url: String = ""
)

@Keep
data class Location(
    val name: String = "unknown",
    val url: String = ""
)

fun Result.mapToDomainCharacterModel(): DomainCharacterModel {
    return DomainCharacterModel(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        org_name = origin.name,
        org_url = origin.url,
        loc_name = location.name,
        loc_url = location.url,
        image = image,
        episode = episode,
        url = url,
        created = created
    )
}