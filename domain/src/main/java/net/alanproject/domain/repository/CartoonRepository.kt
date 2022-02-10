package net.alanproject.domain.repository

import io.reactivex.rxjava3.core.Single
import net.alanproject.domain.model.*

interface CartoonRepository {

    fun getCharacter(id:Long): Single<DomainCharacterModel>
    fun getCharacters(ids:String):Single<List<DomainCharacterModel>>
    fun getAllCharacter(page:Long):Single<List<DomainCharacterModel>>

    fun getEpisode(id:Long?): Single<DomainEpiModel>
    fun getEpisodeList(season:String): Single<List<DomainEpiFromAsset>>
    fun getEpisodeTitle(id: Long): Single<DomainEpiFromAsset>
    fun matchEpisode(episodes: List<String>): Single<List<DomainEpiFromAsset>>

    fun getLocation(page:Long):Single<List<DomainLocationModel>>
    fun searchCharacter(page:Long =0 , name: String): Single<List<DomainCharacterModel>>
    fun searchLocation(page: Long, name: String): Single<List<DomainLocationModel>>

    fun getQuoteFromServer():Single<List<DomainQuoteModel>>
    fun getQuoteFromAsset():List<DomainQuoteModel>
    fun getVersionConfig():DomainVersionConfigModel


}