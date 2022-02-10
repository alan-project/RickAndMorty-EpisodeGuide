package net.alanproject.domain.usecases

import io.reactivex.rxjava3.core.Single
import net.alanproject.domain.model.DomainVersionConfigModel

interface GetVersionConfig {
    fun getNewVersionConfig(): Pair<Boolean,Boolean>
}