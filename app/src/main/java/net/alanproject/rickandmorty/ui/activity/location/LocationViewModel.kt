package net.alanproject.rickandmorty.ui.activity.location

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
import net.alanproject.domain.model.DomainLocationModel
import net.alanproject.domain.usecases.GetCharacter
import net.alanproject.rickandmorty.common.RxSchedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val getCharacter: GetCharacter,
    private val rxSchedulers: RxSchedulers,
    private val disposable: CompositeDisposable

) : ViewModel() {

    private val _characterList: MutableLiveData<List<DomainCharacterModel>> = MutableLiveData()
    val characterList: LiveData<List<DomainCharacterModel>>
        get() = _characterList

    private val _progressBarVisibility: MutableLiveData<Int> = MutableLiveData(View.VISIBLE)
    val progressBarVisibility: LiveData<Int>
        get() = _progressBarVisibility

    fun getResidents(locationModel: DomainLocationModel) {
        val ids = extractCharIds(locationModel)
        Timber.d("extractCharIds: $ids")
        if(ids.isBlank()){
            _characterList.value = null
            Timber.d("[LocationError] set null and return")
            _progressBarVisibility.value = View.GONE
            return
        }
        Timber.d("[LocationError] progressBar is Visible")
        _progressBarVisibility.value = View.VISIBLE
        getCharacter.getCharacters(ids)
            .observeOn(rxSchedulers.ui)
            .subscribeOn(rxSchedulers.io)
            .doOnTerminate {
                _progressBarVisibility.value = View.GONE
            }
            .subscribe({ characterList ->
                Timber.d("getAllCharacter: $characterList")
                _characterList.value = characterList
            }, {
                Firebase.crashlytics.recordException(it)
                Timber.e("Throwable: $it")
                _characterList.value = null

            })
            .addTo(disposable)
    }


    private fun extractCharIds(locationModel: DomainLocationModel): String {
        return locationModel.residents.joinToString {
            it.replace("https://rickandmortyapi.com/api/character/", "")
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}