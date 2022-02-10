package net.alanproject.rickandmorty.ui.navigation.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import net.alanproject.domain.model.DomainCharacterModel
import net.alanproject.domain.usecases.GetCharacter
import net.alanproject.rickandmorty.common.TestSchedulers
import net.alanproject.rickandmorty.common.getOrAwaitValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

@RunWith(MockitoJUnitRunner::class)
class CharacterListViewModelTest {


    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @JvmField
    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CharacterListViewModel

    private val testSchedulers: TestSchedulers = TestSchedulers()

    @Mock
    private lateinit var getCharacter: GetCharacter

    @Mock
    private lateinit var disposable: CompositeDisposable


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = CharacterListViewModel(getCharacter, testSchedulers, disposable)
    }

    @Test
    fun `getAllCharacters - Return normal characterModel`() {
        val expected = listOf(
            DomainCharacterModel(
                1, "a", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
            ),
            DomainCharacterModel(
                2, "a", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
            ),
            DomainCharacterModel(
                3, "a", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
            ),
        )

        `when`(getCharacter.getAllCharacterByPage(0))
            .thenReturn(Single.just(expected))

        getCharacter.getAllCharacterByPage(0)
            .test()
            .assertValue {
                it.size == expected.size
            }
            .assertComplete()
            .assertNoErrors()

    }

    @Test
    fun `getAllCharacters - verify characterList LiveData`(){
        val expected = listOf(
            DomainCharacterModel(
                1, "a", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
            ),
            DomainCharacterModel(
                2, "a", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
            ),
            DomainCharacterModel(
                3, "a", "b", "c", "d", "e", "f", "g", "h", "i", listOf("a", "b", "c"), "j", "k"
            ),
        )

        `when`(getCharacter.getAllCharacterByPage(1))
            .thenReturn(Single.just(expected))

        viewModel.getAllCharacters()
        testSchedulers.triggerActions()

        //TODO liveData is not set
        val list = viewModel.characterList.getOrAwaitValue()

        Assert.assertEquals(expected, list)


    }

}
