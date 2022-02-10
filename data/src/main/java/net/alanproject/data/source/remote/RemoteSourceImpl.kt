package net.alanproject.data.source.remote

import android.content.Context
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Single
import net.alanproject.data.common.parseQuotesJson
import net.alanproject.data.source.RemoteSource
import net.alanproject.data.source.remote.api.mapToDomain
import net.alanproject.data.source.remote.api.mapToDomainCharacterModel
import net.alanproject.data.source.remote.api.mapToDomainEpiModel
import net.alanproject.domain.model.*
import timber.log.Timber
import javax.inject.Inject


class RemoteSourceImpl @Inject constructor(
    private val api: CartoonApi,
    private val database: DatabaseReference,
    private val remoteConfig: FirebaseRemoteConfig,
    @ApplicationContext val context: Context
) : RemoteSource {


    override fun getCharacter(id: Long): Single<DomainCharacterModel> {
        return api.getCharacter(id).map { result ->

//            Timber.d("[RemoteSourceImpl] getCharacter $result")
            result.mapToDomainCharacterModel()
        }
    }

    override fun getAllCharacter(page: Long): Single<List<DomainCharacterModel>> {
        return api.getAllCharacter(page).map { characterDto ->
//            Timber.d("[RemoteSourceImpl] getAllCharacter $characterDto")
            characterDto.results.map { result ->
                result.mapToDomainCharacterModel()
            }
        }
    }

    override fun getCharacters(ids: String): Single<List<DomainCharacterModel>> {
        return api.getCharacters(ids).map { resultList ->
            Timber.d("[RemoteSourceImpl] getCharacters $resultList")
            resultList.map {
                it.mapToDomainCharacterModel()
            }
        }
    }

    override fun searchCharacter(page: Long, name: String): Single<List<DomainCharacterModel>> {
//        Timber.d("searchCharacter: $name")
        Timber.d("searchCharacter In")
        return api.searchCharacter(page, name).map { characterDto ->
//            Timber.d(" searchCharacter: $characterDto")
            characterDto.results.map { result ->
                result.mapToDomainCharacterModel()
            }
        }
    }

    override fun searchLocation(page: Long, name: String): Single<List<DomainLocationModel>> {
//        Timber.d("searchLocation: $name")
        Timber.d("searchLocation In")

        return api.searchLocation(page, name).map { locationDto ->
            locationDto.results.map { locationInfo ->
                locationInfo.mapToDomain()
            }
        }
    }


    override fun getQuote(): Single<List<DomainQuoteModel>> {
        return Single.create { emitter ->
            database.child("list")
                .get()
                .addOnSuccessListener { dataSnapShot ->
                    Timber.d("firebase: Got value ${dataSnapShot.value}")
                    val value = dataSnapShot.getValue(Object::class.java)
                    val json = Gson().toJson(value)
                    Timber.d("quotes: $json")
                    emitter.onSuccess(parseQuotesJson(json))
                }.addOnFailureListener {
                    Timber.e("firebase: addOnFailureListener ${it.message}")
                    Firebase.crashlytics.recordException(it)
//                    emitter.onError(it)
                    emitter.onSuccess(listOf())
                }

        }
    }

    override fun getVersionConfig(): DomainVersionConfigModel {

        Timber.d("getVersionConfig IN")

        val domainVersionConfigModel = Gson().fromJson(
            remoteConfig.getString("version_update"),
            DomainVersionConfigModel::class.java
        ) ?: DomainVersionConfigModel(
            latestVersion = context.packageManager.getPackageInfo(
                context.packageName,
                0
            ).versionCode
        )

        Timber.d("domainVersionConfigModel: $domainVersionConfigModel")
        return domainVersionConfigModel
    }

    override fun getEpisode(page: Long?): Single<DomainEpiModel> {
        return api.getEpisode(page).map { episodeDto ->
            Timber.d("[RemoteSourceImpl] getEpisode $episodeDto")
            episodeDto.mapToDomainEpiModel()
        }
    }

    override fun getLocation(page: Long): Single<List<DomainLocationModel>> {
        Timber.d("getLocation In")

        return api.getLocation(page).map { locationDto ->
            locationDto.results.map { locationInfo ->
                locationInfo.mapToDomain()
            }
        }
    }
}