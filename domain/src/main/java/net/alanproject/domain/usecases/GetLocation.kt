package net.alanproject.domain.usecases

import io.reactivex.rxjava3.core.Single
import net.alanproject.domain.model.DomainCharacterModel
import net.alanproject.domain.model.DomainEpiFromAsset
import net.alanproject.domain.model.DomainEpiModel
import net.alanproject.domain.model.DomainLocationModel

interface GetLocation {

    fun getLocations(page: Long): Single<List<DomainLocationModel>>
    fun searchLocations(page: Long = 0, name: String): Single<List<DomainLocationModel>>
}