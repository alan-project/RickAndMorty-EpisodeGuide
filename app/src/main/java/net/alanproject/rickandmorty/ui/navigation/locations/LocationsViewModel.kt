package net.alanproject.rickandmorty.ui.navigation.locations

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import net.alanproject.domain.model.DomainLocationModel
import net.alanproject.domain.usecases.GetLocation
import net.alanproject.rickandmorty.common.RxSchedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val getLocation: GetLocation,
    private val rxSchedulers: RxSchedulers,
    private val disposable: CompositeDisposable
) : ViewModel() {

    private val _locations: MutableLiveData<List<DomainLocationModel>> = MutableLiveData()
    val locations: LiveData<List<DomainLocationModel>>
        get() = _locations

    private val _progressBarVisibility: MutableLiveData<Int> = MutableLiveData(View.VISIBLE)
    val progressBarVisibility: LiveData<Int>
        get() = _progressBarVisibility

    private var curPage: Long = 1

    private var isLoading = false
    private var isFull = false

    fun getAllLocations() {

        if (isLoading || isFull) return
        isLoading = true

        _progressBarVisibility.value = View.VISIBLE

        getLocation.getLocations(curPage++)
            .observeOn(rxSchedulers.ui)
            .subscribeOn(rxSchedulers.io)
            .doOnTerminate {
                isLoading = false
                _progressBarVisibility.value = View.GONE
            }
            .subscribe({ locationList ->
//                Timber.d("getLocations: $locationList")
                if (locationList.isNullOrEmpty()) {
                    isFull = true
                    return@subscribe
                }
                _locations.value = locationList
            }, {
                Firebase.crashlytics.recordException(it)
                Timber.e("Throwable: $it")
            })
            .addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}