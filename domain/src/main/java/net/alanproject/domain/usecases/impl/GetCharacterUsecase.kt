package net.alanproject.domain.usecases.impl

import io.reactivex.rxjava3.core.Single
import net.alanproject.domain.model.DomainCharacterModel
import net.alanproject.domain.repository.CartoonRepository
import net.alanproject.domain.usecases.GetCharacter
import javax.inject.Inject

class GetCharacterUsecase @Inject constructor(
    private val  repository: CartoonRepository
):GetCharacter {
    override fun getAllCharacterByPage(page: Long): Single<List<DomainCharacterModel>> {
        return repository.getAllCharacter(page)
    }

    override fun searchCharacter(page:Long, name: String): Single<List<DomainCharacterModel>> {
        return repository.searchCharacter(page,name)
            .onErrorReturnItem(listOf())
    }

    override fun getCharacters(ids: String): Single<List<DomainCharacterModel>> {
        return repository.getCharacters(ids)
    }

    override fun getCharacter(id: Long): Single<DomainCharacterModel> {
        return repository.getCharacter(id)
    }
}