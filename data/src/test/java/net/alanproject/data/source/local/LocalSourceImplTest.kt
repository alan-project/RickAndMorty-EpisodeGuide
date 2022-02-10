package net.alanproject.data.source.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Single
import net.alanproject.data.source.LocalSource
import net.alanproject.domain.model.DomainEpiFromAsset
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class LocalSourceImplTest {

    @Mock
    private lateinit var context: Context

    private lateinit var localSource:LocalSource

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        localSource = LocalSourceImpl(context)
    }

/*    @Test
    fun `getEpisodesBySeason - normal case`() {

        val expected = listOf(
            DomainEpiFromAsset(0,"a","b","c","d"),
            DomainEpiFromAsset(1,"a","b","c","d"),
            DomainEpiFromAsset(2,"a","b","c","d")
        )

        `when`(localSource.getEpisodesBySeason("1"))
            .thenReturn(Single.just(expected))


    }

    @Test
    fun `getEpisodeById - normal case`() {
    }

    @Test
    fun `getEpisodesByEpiUrl - normal case`() {
    }*/
}