package net.alanproject.rickandmorty.ui.navigation.locations

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import net.alanproject.domain.model.DomainLocationModel
import net.alanproject.domain.usecases.GetLocation
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
class LocationsViewModelTest {

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @JvmField
    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LocationsViewModel

    private val testSchedulers: TestSchedulers = TestSchedulers()

    @Mock
    private lateinit var getLocation: GetLocation

    @Mock
    private lateinit var disposable: CompositeDisposable

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        viewModel = LocationsViewModel(getLocation,testSchedulers, disposable)
    }

    @Test
    fun `getAllLocations - verify locations livedata`() {
        val expected = listOf(
            DomainLocationModel(1, "2","3","4", listOf(),"5","6"),
            DomainLocationModel(2, "2","3","4", listOf(),"5","6"),
            DomainLocationModel(3, "2","3","4", listOf(),"5","6"),
        )

        `when`(getLocation.getLocations(1))
            .thenReturn(Single.just(expected))

        viewModel.getAllLocations()
        testSchedulers.triggerActions()

        val list = viewModel.locations.getOrAwaitValue()
        Assert.assertEquals(expected,list)
    }
}