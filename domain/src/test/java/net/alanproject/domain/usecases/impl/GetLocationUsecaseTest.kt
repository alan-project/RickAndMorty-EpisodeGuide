package net.alanproject.domain.usecases.impl

import io.reactivex.rxjava3.core.Single
import net.alanproject.domain.model.DomainLocationModel
import net.alanproject.domain.repository.CartoonRepository
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetLocationUsecaseTest {

    @Mock
    private lateinit var repository: CartoonRepository

    private lateinit var getLocationUsecase: GetLocationUsecase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getLocationUsecase = GetLocationUsecase(repository)
    }

    @Test
    fun `getLocations - pass case`() {
        val expected = listOf(
            DomainLocationModel(0,"1","2","3", listOf(),"4","5"),
            DomainLocationModel(1,"1","2","3", listOf(),"4","5"),
            DomainLocationModel(2,"1","2","3", listOf(),"4","5")
        )
        `when`(repository.getLocation(0))
            .thenReturn(Single.just(expected))

        getLocationUsecase.getLocations(0)
            .test()
            .assertValue { actual ->
                actual == expected
            }
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `searchLocations - pass case`() {
        val expected = listOf(
            DomainLocationModel(0,"1","2","3", listOf(),"4","5"),
            DomainLocationModel(1,"1","2","3", listOf(),"4","5"),
            DomainLocationModel(2,"1","2","3", listOf(),"4","5")
        )
        `when`(repository.searchLocation(0,"Rick"))
            .thenReturn(Single.just(expected))

        getLocationUsecase.searchLocations(0,"Rick")
            .test()
            .assertValue { actual ->
                actual == expected
            }
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `searchLocations - error case`(){
        `when`(repository.searchLocation(0,"error"))
            .thenReturn(Single.just(listOf()))

        getLocationUsecase.searchLocations(0,"error")
            .test()
            .assertValue { actual ->
                actual.isEmpty()
            }
            .assertComplete()
            .assertNoErrors()
    }
}