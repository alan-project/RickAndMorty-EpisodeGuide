package net.alanproject.domain.usecases.impl

import io.reactivex.rxjava3.core.Single
import net.alanproject.domain.model.DomainCharacterModel
import net.alanproject.domain.model.DomainEpiFromAsset
import net.alanproject.domain.model.DomainEpiModel
import net.alanproject.domain.repository.CartoonRepository
import net.alanproject.domain.usecases.GetEpisode
import javax.inject.Inject

class GetEpisodeUsecase @Inject constructor(
    private val repository: CartoonRepository
) : GetEpisode {

    override fun getEpisode(id: Long?): Single<Pair<DomainEpiModel, List<DomainCharacterModel>>> {
        return repository.getEpisode(id)
            .flatMap { episode ->
                repository.getCharacters(extractCharIds(episode))
                    .map { charList ->
                        episode to charList
                    }
            }
    }

    override fun getEpisodeListFromAsset(season:String): Single<List<DomainEpiFromAsset>> {
        return repository.getEpisodeList(season)
    }

    override fun matchEpisodeListWithAsset(episodes: List<String>): Single<List<DomainEpiFromAsset>> {
        return repository.matchEpisode(episodes)
    }

    override fun getEpisodeTitleFromAsset(id: Long): Single<DomainEpiFromAsset> {
        return repository.getEpisodeTitle(id)
    }

    private fun extractCharIds(episode: DomainEpiModel): String {
        return episode.characters.joinToString {
            it.character_url.replace("https://rickandmortyapi.com/api/character/", "")
        }
    }
}