package net.alanproject.rickandmorty.ui.activity.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.PublishSubject
import net.alanproject.domain.model.DomainCharacterModel
import net.alanproject.domain.usecases.GetCharacter
import net.alanproject.rickandmorty.common.RxSchedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SearchCharacterViewModel @Inject constructor(
    private val getCharacter: GetCharacter,
    private val rxSchedulers: RxSchedulers,
    private val disposable: CompositeDisposable

) : ViewModel() {

    private val _characters: MutableLiveData<List<DomainCharacterModel>> = MutableLiveData()
    val characters: LiveData<List<DomainCharacterModel>>
        get() = _characters

    private val keywordStream = PublishSubject.create<String>()

    private var curPage: Long = 1
    private var allList: MutableList<DomainCharacterModel> = mutableListOf()

    private var isLoading = false
    private var hasMore = false
    private var keyword: String = ""

    init {
        keywordStream
            .debounce(500, TimeUnit.MILLISECONDS)
            .flatMapSingle {
                Timber.d("searchCharacter: $it")
//                getCharacter.searchCharacter(page = curPage++, it)
                keyword = it
                getCharacter.searchCharacter(page = curPage++, it)
            }
            .observeOn(rxSchedulers.ui)
            .subscribeOn(rxSchedulers.io)
            .subscribe({ characterList ->
//                Timber.d("characterList: $characterList")
//                _characters.value = characterList
                allList = characterList.toMutableList()
                _characters.value = allList
                Timber.d("searchCharacters In")
                Timber.d("allList: $allList")
                hasMore = characterList.isNotEmpty()
            }, {
                Timber.d("Throwable: $it")
                allList.clear()
                hasMore = false
                _characters.value = listOf()
            })
            .addTo(disposable)
    }

    fun searchNextPage() {
        Timber.d("isLoading: $isLoading, hasMore: $hasMore")
        if (isLoading || !hasMore) return
        isLoading = true

        Timber.d("curPage: $curPage")

        getCharacter.searchCharacter(page = curPage++, keyword)
            .observeOn(rxSchedulers.ui)
            .subscribeOn(rxSchedulers.io)
            .doOnTerminate {
                isLoading = false
            }
            .subscribe({ characterList ->
                Timber.d("characterList: $characterList")
                if (characterList.isNullOrEmpty()) {
                    hasMore = false
                    return@subscribe
                }
                isLoading = false
                allList.addAll(characterList)
                _characters.value = allList
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