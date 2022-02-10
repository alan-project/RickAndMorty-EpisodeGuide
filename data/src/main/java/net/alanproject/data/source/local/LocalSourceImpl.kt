package net.alanproject.data.source.local

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Single
import net.alanproject.data.common.parseQuotesJson
import net.alanproject.data.source.LocalSource
import net.alanproject.domain.model.DomainEpiFromAsset
import net.alanproject.domain.model.DomainQuoteModel
import timber.log.Timber
import javax.inject.Inject

class LocalSourceImpl @Inject constructor(
    @ApplicationContext val context: Context
) : LocalSource {

    private val allEpisode: MutableList<DomainEpiFromAsset> = mutableListOf()

    private fun getAllEpisodeListFromAsset() {
        Timber.d("getAllEpisodeListFromAsset")

        val assetManager = context.resources.assets

        assetManager.list("seasons")?.map { file ->
//            Timber.d("file: $file")

            val inputStream = assetManager.open("seasons/$file")
            val jsonString = inputStream.bufferedReader().use { it.readText() }
//            Timber.d("jsonString: $jsonString")

            allEpisode.addAll(
                Gson().fromJson(jsonString, Array<DomainEpiFromAsset>::class.java).asList()
            )
        }

//        Timber.d("[getAllEpisodeListFromAsset] allEpisode:$allEpisode")
//        Timber.d("[getAllEpisodeListFromAsset] allEpisode.size:${allEpisode.size}")
    }

    override fun getEpisodesBySeason(season: String): Single<List<DomainEpiFromAsset>> {
        return Single.create { emitter ->

            if (allEpisode.isNullOrEmpty()) getAllEpisodeListFromAsset()

            val epi: String = "S0$season"
//            Timber.d("epi: $epi")

            emitter.onSuccess(
                allEpisode.filter {
                    it.epi.startsWith(epi)
                }
            )
        }
    }

    override fun getEpisodeById(id: Long): Single<DomainEpiFromAsset> {
        return Single.create { emitter ->

            if (allEpisode.isNullOrEmpty()) getAllEpisodeListFromAsset()

            Timber.d("epiList.first{it.no == id}: ${allEpisode.first { it.no == id }}")
            emitter.onSuccess(
                allEpisode.first { it.no == id }
            )
        }
    }

    override fun getEpisodesByEpiUrl(episodes: List<String>): Single<List<DomainEpiFromAsset>> {
        return Single.create { emitter ->

            if (allEpisode.isNullOrEmpty()) getAllEpisodeListFromAsset()

            val epiList: MutableList<DomainEpiFromAsset> = mutableListOf()
            episodes.map { url ->

                val epId: Long =
                    url.replace("https://rickandmortyapi.com/api/episode/", "").toLong()
                epiList.addAll(allEpisode.filter {
                    it.no == epId
                })
            }

//            Timber.d("[getEpisodesByEpiUrl] epiList: $epiList")

            emitter.onSuccess(epiList)
        }
    }

    override fun getQuote(): List<DomainQuoteModel> {
        val assetManager = context.resources.assets
        val inputStream = assetManager.open("quote/quote.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        Timber.d("getQuote from Asset")
//        Timber.d("jsonString: $jsonString")
        return parseQuotesJson(jsonString)

    }
}