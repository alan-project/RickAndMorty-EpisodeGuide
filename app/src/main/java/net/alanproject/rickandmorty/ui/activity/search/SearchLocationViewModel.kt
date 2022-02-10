package net.alanproject.rickandmorty.ui.activity.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.PublishSubject
import net.alanproject.domain.model.DomainLocationModel
import net.alanproject.domain.usecases.GetLocation
import net.alanproject.rickandmorty.common.RxSchedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SearchLocationViewModel @Inject constructor(
    private val getLocation: GetLocation,
    private val rxSchedulers: RxSchedulers,
    private val disposable: CompositeDisposable
) : ViewModel() {

    private val _locations: MutableLiveData<List<DomainLocationModel>> = MutableLiveData()
    val locations: LiveData<List<DomainLocationModel>>
        get() = _locations

    private val keywordStream = PublishSubject.create<String>()

    private var curPage: Long = 1
    private var allList: MutableList<DomainLocationModel> = mutableListOf()

    private var isLoading = false
    private var hasMore = false
    private var keyword: String = ""

    init {
        keywordStream
            .debounce(500, TimeUnit.MILLISECONDS)
            .flatMapSingle {

                keyword = it
                getLocation.searchLocations(page = curPage++, it)
            }
            .observeOn(rxSchedulers.ui)
            .subscribeOn(rxSchedulers.io)
            .subscribe({ locationList ->
                Timber.d("keywordStream In")
                Timber.d("keywordStream: $locationList")

                allList = locationList.toMutableList()

                _locations.value = allList
                Timber.d("_locations: ${_locations.value}")
                hasMore = locationList.isNotEmpty()
            }, {
                Timber.d("Throwable: $it")
                allList.clear()
                hasMore = false
                _locations.value = listOf()
            })
            .addTo(disposable)
    }


    fun searchNextPage() {
        Timber.d("isLoading: $isLoading, hasMore: $hasMore")
        if (isLoading || !hasMore) return
        isLoading = true

        Timber.d("curPage: $curPage")

        getLocation.searchLocations(page = curPage++, keyword)
            .observeOn(rxSchedulers.ui)
            .subscribeOn(rxSchedulers.io)
            .doOnTerminate {
                isLoading = false
            }
            .subscribe({ locationList ->
//                Timber.d("locationList: $locationList")
                Timber.d("searchNextPage In")
                if (locationList.isNullOrEmpty()) {
                    Timber.d("locationList.isNullOrEmpty(): ${locationList.isNullOrEmpty()}")
                    hasMore = false
                    return@subscribe
                }
                isLoading = false
                allList.addAll(locationList)
                _locations.value = allList
            }, {
                Timber.d("Throwable: $it")
//                _characters.value = listOf()
            })
            .addTo(disposable)
    }

    fun search(name: String) {

        Timber.d("search: $name")
        curPage = 1
        keywordStream.onNext(name)

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}