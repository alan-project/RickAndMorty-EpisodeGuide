package net.alanproject.data.source

import io.reactivex.rxjava3.core.Single
import net.alanproject.domain.model.DomainEpiFromAsset
import net.alanproject.domain.model.DomainQuoteModel

interface LocalSource {
    fun getEpisodesBySeason(season:String): Single<List<DomainEpiFromAsset>>
    fun getEpisodeById(id: Long): Single<DomainEpiFromAsset>
    fun getEpisodesByEpiUrl(episodes: List<String>): Single<List<DomainEpiFromAsset>>

    fun getQuote():List<DomainQuoteModel>
}