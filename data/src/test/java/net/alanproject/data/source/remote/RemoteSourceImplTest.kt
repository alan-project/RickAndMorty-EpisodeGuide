package net.alanproject.data.source.remote

import com.google.firebase.database.DatabaseReference
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import io.reactivex.rxjava3.core.Single
import net.alanproject.data.source.RemoteSource
import net.alanproject.data.source.remote.api.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class RemoteSourceImplTest {

    @Mock
    private lateinit var api: CartoonApi

    @Mock
    private lateinit var database: DatabaseReference

    @Mock
    private lateinit var remoteConfig: FirebaseRemoteConfig

    private lateinit var remoteSource: RemoteSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        remoteSource = RemoteSourceImpl(api,database, remoteConfig)
    }

    @Test
    fun `getCharacter - normal case`() {

        val expectedResult =
            Result(0, "1", "2", "3", "4", Origin(), Location(), "5", listOf(), "6", "7")
//
//        val expectedCharacter = DomainCharacterModel(
//            0, "1", "2", "3", "4", "5", "6", "7", "8", "9", listOf("a", "b", "c"), "10", "11"
//        )

        `when`(api.getCharacter(0))
            .thenReturn(Single.just(expectedResult))


        remoteSource.getCharacter(0)
            .test()
            .assertValue { actual ->
                actual.id == expectedResult.id

            }
            .assertComplete()
            .assertNoErrors()
    }


    @Test
    fun `getCharacter - exception case`() {

        `when`(api.getCharacter(0))
            .thenReturn(Single.error(Exception("db broken")))

        remoteSource.getCharacter(0)
            .test()
            .assertError(Exception::class.java)
    }

    @Test
    fun `getAllCharacter - normal case`() {
        val results = listOf(
            Result(0, "1", "2", "3", "4", Origin(), Location(), "5", listOf(), "6", "7"),
            Result(1, "1", "2", "3", "4", Origin(), Location(), "5", listOf(), "6", "7"),
            Result(2, "1", "2", "3", "4", Origin(), Location(), "5", listOf(), "6", "7")
        )
        val expected = CharacterDto(
            results = results
        )

        `when`(api.getAllCharacter(0))
            .thenReturn(Single.just(expected))

        remoteSource.getAllCharacter(0)
            .test()
            .assertValue { actual ->
                actual.size == results.size
            }
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun `getAllCharacter - exception case`() {

        `when`(api.getAllCharacter(0))
            .thenReturn(Single.error(Exception("db broken")))

        remoteSource.getAllCharacter(0)
            .test()
            .assertError(Exception::class.java)
    }

    @Test
    fun `getCharacters - normal case`() {
        val expected = listOf(
            Result(0, "1", "2", "3", "4", Origin(), Location(), "5", listOf(), "6", "7"),
            Result(1, "1", "2", "3", "4", Origin(), Location(), "5", listOf(), "6", "7"),
            Result(2, "1", "2", "3", "4", Origin(), Location(), "5", listOf(), "6", "7")
        )

        `when`(api.getCharacters("1,2,3"))
            .thenReturn(Single.just(expected))

        remoteSource.getCharacters("1,2,3")
            .test()
            .assertValue { actual ->
                actual.size == expected.size
            }
            .assertNoErrors()
            .assertComplete()
    }

    //TODO exception case

    @Test
    fun `searchCharacter - normal case`() {

        val results = listOf(
            Result(0, "1", "2", "3", "4", Origin(), Location(), "5", listOf(), "6", "7"),
            Result(1, "1", "2", "3", "4", Origin(), Location(), "5", listOf(), "6", "7"),
            Result(2, "1", "2", "3", "4", Origin(), Location(), "5", listOf(), "6", "7")
        )
        val expected = CharacterDto(
            results = results
        )

        `when`(api.searchCharacter(0, "Rick"))
            .thenReturn(Single.just(expected))

        remoteSource.searchCharacter(0, "Rick")
            .test()
            .assertValue { actual ->
                actual.size == results.size
            }
            .assertNoErrors()
            .assertComplete()
    }

    //TODO exception case

    @Test
    fun `searchLocation - normal case`() {

        val results = listOf(
            LocationInfo(0, "a", "b", "c", listOf(), "d", "e"),
            LocationInfo(1, "a", "b", "c", listOf(), "d", "e"),
            LocationInfo(2, "a", "b", "c", listOf(), "d", "e")
        )

        val expected = LocationDto(
            results = results
        )

        `when`(api.searchLocation(0, "Rick"))
            .thenReturn(Single.just(expected))

        remoteSource.searchLocation(0, "Rick")
            .test()
            .assertValue { actual ->
                actual.size == results.size
            }
            .assertNoErrors()
            .assertComplete()

    }

    //TODO exception case

    @Test
    fun `getEpisode - normal case`() {
        val expected = EpisodeDto(0, "a", "b", "c", listOf(), "d", "e")

        `when`(api.getEpisode(0))
            .thenReturn(Single.just(expected))

        remoteSource.getEpisode(0)
            .test()
            .assertValue { actual ->
                actual.id == expected.id
            }
            .assertNoErrors()
            .assertComplete()
    }

    //TODO exception case

    @Test
    fun `getLocation - normal case`() {

        val results = listOf(
            LocationInfo(0, "a", "b", "c", listOf(), "d", "e"),
            LocationInfo(1, "a", "b", "c", listOf(), "d", "e"),
            LocationInfo(2, "a", "b", "c", listOf(), "d", "e")
        )

        val expected = LocationDto(
            results = results
        )

        `when`(api.getLocation(0))
            .thenReturn(Single.just(expected))

        remoteSource.getLocation(0)
            .test()
            .assertValue {actual->
                actual.size== results.size
            }
            .assertNoErrors()
            .assertComplete()

    }

    //TODO exception case
}