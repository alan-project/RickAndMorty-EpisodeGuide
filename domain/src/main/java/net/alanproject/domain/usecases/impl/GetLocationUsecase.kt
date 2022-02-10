package net.alanproject.domain.usecases.impl

import io.reactivex.rxjava3.core.Single
import net.alanproject.domain.model.DomainLocationModel
import net.alanproject.domain.repository.CartoonRepository
import net.alanproject.domain.usecases.GetLocation
import javax.inject.Inject

class GetLocationUsecase @Inject constructor(
    private val repository: CartoonRepository
):GetLocation {

    override fun getLocations(page:Long): Single<List<DomainLocationModel>> {
        return repository.getLocation(page)
    }

    override fun searchLocations(page: Long, name: String): Single<List<DomainLocationModel>> {
        return repository.searchLocation(page,name)
            .onErrorReturn{listOf()}
    }
}