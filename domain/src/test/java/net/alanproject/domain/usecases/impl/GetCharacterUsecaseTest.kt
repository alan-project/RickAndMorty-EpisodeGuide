package net.alanproject.domain.usecases.impl

import io.reactivex.rxjava3.core.Single
import net.alanproject.domain.model.DomainCharacterModel
import net.alanproject.domain.repository.CartoonRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetCharacterUsecaseTest {

    @Mock
    private lateinit var repository: CartoonRepository

    private lateinit var getCharacterUsecase: GetCharacterUsecase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getCharacterUsecase = GetCharacterUsecase(repository)

    }

    @Test
    fun `getAllCharacterByPage - pass case`() {

        val expected = listOf(
            DomainCharacterModel(
                1, "a", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
            ),
            DomainCharacterModel(
                2, "a", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
            ),
            DomainCharacterModel(
                3, "a", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
            )
        )

        `when`(repository.getAllCharacter(0))
            .thenReturn(Single.just(expected))

        getCharacterUsecase.getAllCharacterByPage(0)
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

        `when`(repository.searchCharacter(0, "Rick"))
            .thenReturn(Single.just(expected))

        getCharacterUsecase.searchCharacter(0, "Rick")
            .test()
            .assertValue { actual ->
                actual == expected
            }
            .assertComplete()
            .assertNoErrors()


    }

    @Test
    fun `searchCharacter - error case`(){

        `when`(repository.searchCharacter(0,"error"))
            .thenReturn(Single.just(listOf()))

        getCharacterUsecase.searchCharacter(0, "error")
            .test()
            .assertValue { actual ->
                actual.isEmpty()
            }
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `getCharacters - pass case`() {

        val expected = listOf(
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

        `when`(repository.getCharacters("ids"))
            .thenReturn(Single.just(expected))

        getCharacterUsecase.getCharacters("ids")
            .test()
            .assertValue { actual ->
                actual == expected
            }
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `getCharacter - pass case`() {
        val expected = DomainCharacterModel(
            1, "a", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
        )

        `when`(repository.getCharacter(0))
            .thenReturn(Single.just(expected))

        getCharacterUsecase.getCharacter(0)
            .test()
            .assertValue { actual ->
                actual == expected
            }
            .assertComplete()
            .assertNoErrors()

    }
}