package net.alanproject.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DomainCharacterModel(

    val id: Long = 0,
    val name: String = "unknown",
    val status: String = "unknown",
    val species: String = "unknown",
    val gender: String = "unknown",
    val org_name: String = "unknown",
    val org_url: String ="",
    val loc_name: String = "unknown",
    val loc_url: String = "",
    val image: String = "",
    val episode: List<String> = listOf(),
    val url: String = "",
    val created: String = ""

):Parcelable


