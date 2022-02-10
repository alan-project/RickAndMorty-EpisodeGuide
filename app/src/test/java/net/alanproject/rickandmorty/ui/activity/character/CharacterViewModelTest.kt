package net.alanproject.rickandmorty.ui.activity.character

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import net.alanproject.domain.model.DomainEpiFromAsset
import net.alanproject.domain.usecases.GetEpisode
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
class CharacterViewModelTest {

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @JvmField
    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testSchedulers: TestSchedulers = TestSchedulers()

    @Mock
    private lateinit var disposable: CompositeDisposable

    private lateinit var viewModel: CharacterViewModel

    @Mock
    private lateinit var getEpisode: GetEpisode

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = CharacterViewModel(getEpisode, testSchedulers, disposable)
    }

    @Test
    fun getEpisode() {

        val expected = listOf(
            DomainEpiFromAsset(0,"1","2","3","4"),
            DomainEpiFromAsset(1,"1","2","3","4"),
            DomainEpiFromAsset(2,"1","2","3","4")
        )
        val episodes = listOf(
            "0","1","2"
        )

        `when`(getEpisode.matchEpisodeListWithAsset(episodes))
            .thenReturn(Single.just(expected))

        viewModel.getEpisode(episodes)
        testSchedulers.triggerActions()

        val list = viewModel.episode.getOrAwaitValue()
        Assert.assertEquals(expected,list)

    }
}