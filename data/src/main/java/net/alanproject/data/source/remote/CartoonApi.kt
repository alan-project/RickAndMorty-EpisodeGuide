package net.alanproject.data.source.remote

import io.reactivex.rxjava3.core.Single
import net.alanproject.data.source.remote.api.CharacterDto
import net.alanproject.data.source.remote.api.EpisodeDto
import net.alanproject.data.source.remote.api.LocationDto
import net.alanproject.data.source.remote.api.Result
import net.alanproject.domain.model.DomainLocationModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CartoonApi {

    @GET("/api/character/")
    fun getAllCharacter(
        @Query("page") page: Long = 0
    ): Single<CharacterDto>

    @GET("/api/character/")
    fun searchCharacter(
        @Query("page") page: Long = 0,
        @Query("name") name: String = ""
    ): Single<CharacterDto>

    @GET("/api/character/{ids}")
    fun getCharacters(
        @Path("ids") ids: String = ""
    ): Single<List<Result>>

    @GET("/api/character/{id}")
    fun getCharacter(
        @Path("id") id: Long = 0
    ): Single<Result>

    @GET("/api/episode/{id}")
    fun getEpisode(
        @Path("id") id: Long? = 0
    ): Single<EpisodeDto>

    @GET("/api/location/")
    fun getLocation(
        @Query("page") page: Long = 0
    ): Single<LocationDto>

    @GET("/api/location/")
    fun searchLocation(
        @Query("page") page: Long = 0,
        @Query("name") name: String = ""
    ): Single<LocationDto>


}