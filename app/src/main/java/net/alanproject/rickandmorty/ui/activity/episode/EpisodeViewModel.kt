package net.alanproject.rickandmorty.ui.activity.episode

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
import net.alanproject.domain.model.DomainEpiFromAsset
import net.alanproject.domain.model.DomainEpiModel
import net.alanproject.domain.usecases.GetEpisode
import net.alanproject.rickandmorty.common.RxSchedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val getEpisode: GetEpisode,
    private val rxSchedulers: RxSchedulers,
    private val disposable: CompositeDisposable
) : ViewModel() {

    private val _episode: MutableLiveData<DomainEpiModel> = MutableLiveData()
    val episode: LiveData<DomainEpiModel>
        get() = _episode

    private val _episodeTitleFromAsset: MutableLiveData<DomainEpiFromAsset> = MutableLiveData()
    val episodeTitleFromAsset: LiveData<DomainEpiFromAsset>
        get() = _episodeTitleFromAsset


    private val _characterList: MutableLiveData<List<DomainCharacterModel>> = MutableLiveData()
    val characterList: LiveData<List<DomainCharacterModel>>
        get() = _characterList

    private val _progressBarVisibility: MutableLiveData<Int> = MutableLiveData(View.VISIBLE)
    val progressBarVisibility: LiveData<Int>
        get() = _progressBarVisibility


    fun getEpisode(epId: Long = 0) {

        Timber.d("epiID: $epId")

        _progressBarVisibility.value = View.VISIBLE

        getEpisode.getEpisode(epId)
            .observeOn(rxSchedulers.ui)
            .subscribeOn(rxSchedulers.io)
            .subscribe({ (episode, charList) ->
                Timber.d("getEpisode: $episode")
                _episode.value = episode
                _characterList.value = charList
            }, {
                Firebase.crashlytics.recordException(it)
                Timber.d("Throwable: $it")
            })
            .addTo(disposable)
    }

    fun getEpisodeTitleFromAsset(epId: Long) {

        Timber.d("loadEpisodeTitleFromAsset: $epId")

        getEpisode.getEpisodeTitleFromAsset(epId)
            .observeOn(rxSchedulers.ui)
            .subscribeOn(rxSchedulers.io)
            .doOnTerminate {
                _progressBarVisibility.value = View.GONE
            }
            .subscribe({ domainEpiFromAsset ->
                Timber.d("loadEpisodeTitleFromAsset: $domainEpiFromAsset")
                _episodeTitleFromAsset.value = domainEpiFromAsset
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