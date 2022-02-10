package net.alanproject.rickandmorty.ui.navigation.quote

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import net.alanproject.domain.model.DomainQuoteModel
import net.alanproject.domain.usecases.GetQuote
import net.alanproject.rickandmorty.common.RxSchedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val getQuote: GetQuote,
    private val rxSchedulers: RxSchedulers,
    private val disposable: CompositeDisposable
) : ViewModel() {

    private val _quotes: MutableLiveData<List<DomainQuoteModel>> = MutableLiveData()
    val quotes: LiveData<List<DomainQuoteModel>>
        get() = _quotes

    private val _progressBarVisibility: MutableLiveData<Int> = MutableLiveData(View.VISIBLE)
    val progressBarVisibility: LiveData<Int>
        get() = _progressBarVisibility

    fun fetchQuote() {
        Timber.d("fetchListFromServer")

        _progressBarVisibility.value = View.VISIBLE

        getQuote.get()
            .observeOn(rxSchedulers.ui)
            .subscribeOn(rxSchedulers.io)
            .doOnTerminate {
                _progressBarVisibility.value = View.GONE
            }
            .subscribe({ quoteList ->
                _quotes.value = quoteList
                Timber.d("subscribe: $quoteList")
            }, {
                Firebase.crashlytics.recordException(it)
                _quotes.value = listOf()
                Timber.d("Throwable: $it")
            })
            .addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}