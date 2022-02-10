package net.alanproject.domain.usecases

import io.reactivex.rxjava3.core.Single
import net.alanproject.domain.model.DomainCharacterModel
import net.alanproject.domain.model.DomainEpiFromAsset
import net.alanproject.domain.model.DomainEpiModel

interface GetEpisode {

    fun getEpisode(id: Long?): Single<Pair<DomainEpiModel, List<DomainCharacterModel>>>
    fun getEpisodeListFromAsset(season:String):Single<List<DomainEpiFromAsset>>
    fun matchEpisodeListWithAsset(episodes:List<String>):Single<List<DomainEpiFromAsset>>
    fun getEpisodeTitleFromAsset(id:Long):Single<DomainEpiFromAsset>
}