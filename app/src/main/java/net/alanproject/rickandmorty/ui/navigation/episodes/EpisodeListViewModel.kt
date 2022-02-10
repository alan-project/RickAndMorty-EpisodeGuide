package net.alanproject.rickandmorty.ui.navigation.episodes

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import net.alanproject.domain.model.DomainEpiFromAsset
import net.alanproject.domain.usecases.GetEpisode
import net.alanproject.rickandmorty.common.RxSchedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EpisodeListViewModel @Inject constructor(
    private val getEpisode: GetEpisode,
    private val rxSchedulers: RxSchedulers,
    private val disposable: CompositeDisposable
) : ViewModel() {

    private val _episode: MutableLiveData<List<DomainEpiFromAsset>> = MutableLiveData()
    val episode: LiveData<List<DomainEpiFromAsset>>
        get() = _episode

    private val _progressBarVisibility: MutableLiveData<Int> = MutableLiveData(View.VISIBLE)
    val progressBarVisibility: LiveData<Int>
        get() = _progressBarVisibility


    fun getEpisodes(season: String) {
        Timber.d("loadEpisodes: $season")

        _progressBarVisibility.value = View.VISIBLE


        getEpisode.getEpisodeListFromAsset(season)
            .observeOn(rxSchedulers.ui)
            .subscribeOn(rxSchedulers.io)
            .doOnTerminate {
                _progressBarVisibility.value = View.GONE
            }
            .subscribe({
//                Timber.d("getEpisode(): $it")
                _episode.value = it
            }, {
                Firebase.crashlytics.recordException(it)
                Timber.d("[Throwable] : $it")
            })
            .addTo(disposable)

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}