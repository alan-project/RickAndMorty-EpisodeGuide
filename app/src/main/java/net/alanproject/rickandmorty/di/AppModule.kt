package net.alanproject.rickandmorty.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import net.alanproject.domain.usecases.*
import net.alanproject.domain.usecases.impl.*
import net.alanproject.rickandmorty.common.RxSchedulerImpl
import net.alanproject.rickandmorty.common.RxSchedulers


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RxModule {

    @Binds
    abstract fun bindRxScheduler(rx: RxSchedulerImpl): RxSchedulers

    @Binds
    abstract fun bindGetCharacter(getCharacter: GetCharacterUsecase): GetCharacter

    @Binds
    abstract fun bindGetEpisode(getEpisode: GetEpisodeUsecase): GetEpisode

    @Binds
    abstract fun bindGetLocation(getLocation: GetLocationUsecase): GetLocation

    @Binds
    abstract fun bindGetQuote(getQuote: GetQuoteUsecase): GetQuote

    @Binds
    abstract fun bindGetVersionConfig(getVersionConfig: GetVersionConfigUsecase):GetVersionConfig

}