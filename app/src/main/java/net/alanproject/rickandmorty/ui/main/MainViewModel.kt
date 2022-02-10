package net.alanproject.rickandmorty.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import net.alanproject.domain.usecases.GetVersionConfig
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getVersionConfig: GetVersionConfig
) : ViewModel() {

    private val _versionConfig: MutableLiveData<Pair<Boolean,Boolean>> = MutableLiveData()
    val versionConfig: LiveData<Pair<Boolean,Boolean>>
        get() = _versionConfig

    fun checkNewVersion() {
        _versionConfig.value = getVersionConfig.getNewVersionConfig()
    }
}