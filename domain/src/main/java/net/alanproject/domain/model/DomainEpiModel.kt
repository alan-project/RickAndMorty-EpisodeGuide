package net.alanproject.domain.model

import androidx.annotation.Keep

@Keep
data class DomainEpiModel(
    val id: Long = 0,
    val name: String = "unknown",
    val air_date: String = "",
    val episode: String = "",
    val characters: List<Character> = listOf(),
    val url: String = "",
    val created: String = ""
)
@Keep
data class Character(
    val character_url: String = ""
)


