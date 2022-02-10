package net.alanproject.data.repository

import io.reactivex.rxjava3.core.Single
import net.alanproject.data.source.local.LocalSourceImpl
import net.alanproject.data.source.remote.RemoteSourceImpl
import net.alanproject.domain.model.*
import net.alanproject.domain.repository.CartoonRepository
import javax.inject.Inject

class CartoonRepositoryImpl @Inject constructor(
    private val remoteSource: RemoteSourceImpl,
    private val localSource: LocalSourceImpl
) : CartoonRepository {
    override fun getCharacter(id: Long): Single<DomainCharacterModel> {
        return remoteSource.getCharacter(id)
    }

    override fun getCharacters(ids: String): Single<List<DomainCharacterModel>> {
        return remoteSource.getCharacters(ids)
    }

    override fun getAllCharacter(page: Long): Single<List<DomainCharacterModel>> {
        return remoteSource.getAllCharacter(page)
    }

    override fun getEpisode(id: Long?): Single<DomainEpiModel> {
        return remoteSource.getEpisode(id)
    }

    override fun getEpisodeList(season: String): Single<List<DomainEpiFromAsset>> {
        return localSource.getEpisodesBySeason(season)
    }

    override fun getEpisodeTitle(id: Long): Single<DomainEpiFromAsset> {
        return localSource.getEpisodeById(id)
    }

    override fun matchEpisode(episodes: List<String>): Single<List<DomainEpiFromAsset>> {
        return localSource.getEpisodesByEpiUrl(episodes)
    }

    override fun getLocation(page: Long): Single<List<DomainLocationModel>> {
        return remoteSource.getLocation(page)
    }

    override fun searchCharacter(page: Long, name: String): Single<List<DomainCharacterModel>> {
        return remoteSource.searchCharacter(page, name)
    }

    override fun searchLocation(page: Long, name: String): Single<List<DomainLocationModel>> {
        return remoteSource.searchLocation(page, name)
    }

    override fun getQuoteFromServer(): Single<List<DomainQuoteModel>> {
        return remoteSource.getQuote()
    }

    override fun getQuoteFromAsset(): List<DomainQuoteModel> {
        return localSource.getQuote()
    }

    override fun getVersionConfig(): DomainVersionConfigModel {
        return remoteSource.getVersionConfig()
    }
}