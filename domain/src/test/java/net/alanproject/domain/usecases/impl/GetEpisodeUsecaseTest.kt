package net.alanproject.domain.usecases.impl

import io.reactivex.rxjava3.core.Single
import net.alanproject.domain.model.DomainCharacterModel
import net.alanproject.domain.model.DomainEpiFromAsset
import net.alanproject.domain.model.DomainEpiModel
import net.alanproject.domain.repository.CartoonRepository
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetEpisodeUsecaseTest {

    @Mock
    private lateinit var repository: CartoonRepository

    private lateinit var getEpisodeUsecase: GetEpisodeUsecase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getEpisodeUsecase = GetEpisodeUsecase(repository)

    }

    @Test
    fun `getEpisode - pass case`() {
        val episode = DomainEpiModel(0,"1","2","3", listOf(),"4","5")
        val characterList = listOf(
            DomainCharacterModel(
                1, "Rick", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
            ),
            DomainCharacterModel(
                2, "Rick",
                "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
            ),
            DomainCharacterModel(
                3, "Rick", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
            )
        )
        val expected = Pair(episode, characterList)

        `when`(repository.getEpisode(0))
            .thenReturn(Single.just(episode))

        `when`(repository.getCharacters(extractCharIds(episode)))
            .thenReturn(Single.just(characterList))

        getEpisodeUsecase.getEpisode(0)
            .test()
            .assertValue { actual ->
                actual == expected
            }
            .assertComplete()
            .assertNoErrors()

    }

    private fun extractCharIds(episode: DomainEpiModel): String {
        return episode.characters.joinToString {
            it.character_url.replace("https://rickandmortyapi.com/api/character/", "")
        }
    }

    @Test
    fun `getEpisodeListFromAsset - pass case`() {
        val expected = listOf(
            DomainEpiFromAsset(0,"a","b","c","d"),
            DomainEpiFromAsset(1,"a","b","c","d"),
            DomainEpiFromAsset(2,"a","b","c","d")
        )

        `when`(repository.getEpisodeList("1"))
            .thenReturn(Single.just(expected))

        getEpisodeUsecase.getEpisodeListFromAsset("1")
            .test()
            .assertValue { actual ->
                actual == expected
            }
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `matchEpisodeListWithAsset - pass case`() {
        val episodes:List<String> = listOf("0","1","2")
        val expected = listOf(
            DomainEpiFromAsset(0,"a","b","c","d"),
            DomainEpiFromAsset(1,"a","b","c","d"),
            DomainEpiFromAsset(2,"a","b","c","d")
        )
        `when`(repository.matchEpisode(episodes))
            .thenReturn(Single.just(expected))

        getEpisodeUsecase.matchEpisodeListWithAsset(episodes)
            .test()
            .assertValue { actual ->
                actual == expected
            }
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `getEpisodeTitleFromAsset - pass case`() {
        val expected = DomainEpiFromAsset(0,"a","b","c","d")

        `when`(repository.getEpisodeTitle(0))
            .thenReturn(Single.just(expected))

        getEpisodeUsecase.getEpisodeTitleFromAsset(0)
            .test()
            .assertValue { actual ->
                actual == expected
            }
            .assertComplete()
            .assertNoErrors()
    }
}