package net.alanproject.domain.usecases

import io.reactivex.rxjava3.core.Single
import net.alanproject.domain.model.DomainCharacterModel
import net.alanproject.domain.model.DomainEpiFromAsset
import net.alanproject.domain.model.DomainEpiModel
import net.alanproject.domain.model.DomainQuoteModel

interface GetQuote {
    fun get():Single<List<DomainQuoteModel>>
}