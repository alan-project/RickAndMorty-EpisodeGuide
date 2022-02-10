package net.alanproject.rickandmorty.ui.navigation.characters

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import net.alanproject.domain.model.DomainCharacterModel
import net.alanproject.domain.usecases.GetCharacter
import net.alanproject.rickandmorty.common.RxSchedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharacter: GetCharacter,
    private val rxSchedulers: RxSchedulers,
    private val disposable: CompositeDisposable
) : ViewModel() {

    private val _characterList: MutableLiveData<List<DomainCharacterModel>> = MutableLiveData()
    val characterList: LiveData<List<DomainCharacterModel>>
        get() = _characterList

    private val _character: MutableLiveData<DomainCharacterModel> = MutableLiveData()
    val character: LiveData<DomainCharacterModel>
        get() = _character

    private val _progressBarVisibility: MutableLiveData<Int> = MutableLiveData(View.VISIBLE)
    val progressBarVisibility: LiveData<Int>
        get() = _progressBarVisibility

    private var curPage: Long = 1

    private var isLoading = false
    private var isFull = false

    fun getAllCharacters() {

        if (isLoading || isFull) return
        isLoading = true

        _progressBarVisibility.value = View.VISIBLE

        getCharacter.getAllCharacterByPage(curPage++)
            .observeOn(rxSchedulers.ui)
            .subscribeOn(rxSchedulers.io)
            .doOnTerminate {
                isLoading = false
                _progressBarVisibility.value = View.GONE
            }
            .subscribe({ characterList ->
                Timber.d("getAllCharacter: $characterList")
                if (characterList.isNullOrEmpty()) {
                    isFull = true
                    return@subscribe
                }
                _characterList.value = characterList
            }, {
                Firebase.crashlytics.recordException(it)
                Timber.d("Throwable: $it")
            })
            .addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}