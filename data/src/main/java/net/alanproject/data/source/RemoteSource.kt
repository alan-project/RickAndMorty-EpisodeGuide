package net.alanproject.data.source

import io.reactivex.rxjava3.core.Single
import net.alanproject.domain.model.*

interface RemoteSource {

    fun getCharacter(id: Long): Single<DomainCharacterModel>
    fun getAllCharacter(page: Long): Single<List<DomainCharacterModel>>
    fun getCharacters(ids: String): Single<List<DomainCharacterModel>>

    fun getEpisode(page: Long?): Single<DomainEpiModel>

    fun getLocation(page: Long): Single<List<DomainLocationModel>>
    fun searchCharacter(page: Long, name: String): Single<List<DomainCharacterModel>>
    fun searchLocation(page: Long, name: String): Single<List<DomainLocationModel>>

    fun getQuote(): Single<List<DomainQuoteModel>>
    fun getVersionConfig():DomainVersionConfigModel

}