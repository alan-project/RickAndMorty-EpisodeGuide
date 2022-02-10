package net.alanproject.domain.model

import android.os.Parcelable

import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
@Keep
@Parcelize
data class DomainLocationModel(
    val id: Long = 0,
    val name: String = "",
    val type: String = "",
    val dimension: String = "",
    val residents: List<String> = listOf(),
    val url: String = "",
    val created: String = ""
):Parcelable

