package net.alanproject.domain.usecases

import io.reactivex.rxjava3.core.Single
import net.alanproject.domain.model.DomainCharacterModel

interface GetCharacter {

    fun getAllCharacterByPage(page: Long): Single<List<DomainCharacterModel>>
    fun searchCharacter(page:Long = 0, name: String): Single<List<DomainCharacterModel>>
    fun getCharacters(ids: String): Single<List<DomainCharacterModel>>
    fun getCharacter(id: Long): Single<DomainCharacterModel>
}