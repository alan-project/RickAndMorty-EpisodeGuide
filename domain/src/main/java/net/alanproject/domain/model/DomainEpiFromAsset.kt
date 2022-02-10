package net.alanproject.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DomainEpiFromAsset(
    val no: Long =0,
    val epi: String = "",
    val title: String = "",
    val date: String = "",
    val synop: String = ""
) : Parcelable
