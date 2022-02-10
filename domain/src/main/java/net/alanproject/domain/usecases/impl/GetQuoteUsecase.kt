package net.alanproject.domain.usecases.impl

import io.reactivex.rxjava3.core.Single
import net.alanproject.domain.model.DomainQuoteModel
import net.alanproject.domain.repository.CartoonRepository
import net.alanproject.domain.usecases.GetQuote
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class GetQuoteUsecase  @Inject constructor(
    private val repository: CartoonRepository
): GetQuote {

    override fun get(): Single<List<DomainQuoteModel>> {
        return repository.getQuoteFromServer()
            .map { list ->
                if (list.isNullOrEmpty()) {
                    Timber.d("list.isNullOrEmpty() from Server")
                    repository.getQuoteFromAsset()
                } else {
                    Timber.d("!list.isNullOrEmpty(): from Server")
                    list
                }
            }
    }
}