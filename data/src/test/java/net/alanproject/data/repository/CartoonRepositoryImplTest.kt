package net.alanproject.data.repository

import io.reactivex.rxjava3.core.Single
import net.alanproject.data.source.local.LocalSourceImpl
import net.alanproject.data.source.remote.RemoteSourceImpl
import net.alanproject.domain.model.DomainCharacterModel
import net.alanproject.domain.model.DomainEpiFromAsset
import net.alanproject.domain.model.DomainEpiModel
import net.alanproject.domain.model.DomainLocationModel
import net.alanproject.domain.repository.CartoonRepository
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CartoonRepositoryImplTest {

    @Mock
    private lateinit var remoteSource: RemoteSourceImpl

    @Mock
    private lateinit var localSource: LocalSourceImpl

    private lateinit var cartoonRepository: CartoonRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        cartoonRepository = CartoonRepositoryImpl(remoteSource, localSource)
    }

    @Test
    fun `getCharacter - pass case`() {
        val expected = DomainCharacterModel(
            1, "a", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
        )

        `when`(remoteSource.getCharacter(1))
            .thenReturn(Single.just(expected))

        cartoonRepository.getCharacter(1)
            .test()
            .assertValue { actual ->
                actual == expected
            }
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `getCharacters - pass case`() {
        val expected = listOf(
            DomainCharacterModel(
                0, "a", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
            ),
            DomainCharacterModel(
                1, "a", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
            ),
            DomainCharacterModel(
                2, "a", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
            )
        )

        `when`(remoteSource.getCharacters("1,2,3"))
            .thenReturn(Single.just(expected))

        cartoonRepository.getCharacters("1,2,3")
            .test()
            .assertValue { actual ->
                actual == expected
            }
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `getAllCharacter - pass case`() {
        val expected = listOf(
            DomainCharacterModel(
                0, "a", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
            ),
            DomainCharacterModel(
                1, "a", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
            ),
            DomainCharacterModel(
                2, "a", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
            )
        )

        `when`(remoteSource.getAllCharacter(0))
            .thenReturn(Single.just(expected))

        cartoonRepository.getAllCharacter(0)
            .test()
            .assertValue { actual ->
                actual == expected
            }
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `getEpisode - pass case`() {
        val expected = DomainEpiModel(0,"1","2","3", listOf(),"4","5")
        `when`(remoteSource.getEpisode(0))
            .thenReturn(Single.just(expected))

        cartoonRepository.getEpisode(0)
            .test()
            .assertValue { actual ->
                actual == expected
            }
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `getEpisodeList - pass case`() {
        val expected = listOf(
            DomainEpiFromAsset(0,"a","b","c","d"),
            DomainEpiFromAsset(1,"a","b","c","d"),
            DomainEpiFromAsset(2,"a","b","c","d")
        )
        `when`(localSource.getEpisodesBySeason("1"))
            .thenReturn(Single.just(expected))
            .thenReturn(Single.just(expected))

        cartoonRepository.getEpisodeList("1")
            .test()
            .assertValue { actual ->
                actual == expected
            }
            .assertComplete()
            .assertNoErrors()

    }

    @Test
    fun `getEpisodeTitle - pass case`() {
        val expected = DomainEpiFromAsset(0,"a","b","c","d")
        `when`(localSource.getEpisodeById(0))
            .thenReturn(Single.just(expected))

        cartoonRepository.getEpisodeTitle(0)
            .test()
            .assertValue { actual ->
                actual == expected
            }
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `matchEpisode - pass case`() {
        val input = listOf("0","1","2")
        val expected = listOf(
            DomainEpiFromAsset(0,"a","b","c","d"),
            DomainEpiFromAsset(1,"a","b","c","d"),
            DomainEpiFromAsset(2,"a","b","c","d")
        )

        `when`(localSource.getEpisodesByEpiUrl(input))
            .thenReturn(Single.just(expected))

        cartoonRepository.matchEpisode(input)
            .test()
            .assertValue { actual ->
                actual == expected
            }
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `getLocation - pass case`() {
        val expected = listOf(
            DomainLocationModel(0,"1","2","3", listOf(),"4","5"),
            DomainLocationModel(1,"1","2","3", listOf(),"4","5"),
            DomainLocationModel(2,"1","2","3", listOf(),"4","5")
        )

        `when`(remoteSource.getLocation(0))
            .thenReturn(Single.just(expected))

        cartoonRepository.getLocation(0)
            .test()
            .assertValue { actual ->
                actual == expected
            }
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `searchCharacter - pass case`() {
        val expected = listOf(
            DomainCharacterModel(
                0, "a", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
            ),
            DomainCharacterModel(
                1, "a", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
            ),
            DomainCharacterModel(
                2, "a", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
            )
        )

        `when`(remoteSource.searchCharacter(0,"Rick"))
            .thenReturn(Single.just(expected))

        cartoonRepository.searchCharacter(0,"Rick")
            .test()
            .assertValue { actual ->
                actual == expected
            }
            .assertComplete()
            .assertNoErrors()

    }

    @Test
    fun `searchLocation - pass case`() {
        val expected = listOf(
            DomainLocationModel(0,"1","2","3", listOf(),"4","5"),
            DomainLocationModel(1,"1","2","3", listOf(),"4","5"),
            DomainLocationModel(2,"1","2","3", listOf(),"4","5")
        )
        `when`(remoteSource.searchLocation(0,"Rick"))
            .thenReturn(Single.just(expected))

        cartoonRepository.searchLocation(0,"Rick")
            .test()
            .assertValue { actual ->
                actual == expected
            }
            .assertComplete()
            .assertNoErrors()
    }
}