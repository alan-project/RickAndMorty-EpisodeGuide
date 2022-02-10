package net.alanproject.data.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.alanproject.data.repository.CartoonRepositoryImpl
import net.alanproject.data.source.LocalSource
import net.alanproject.data.source.RemoteSource
import net.alanproject.data.source.local.LocalSourceImpl
import net.alanproject.data.source.remote.CartoonApi
import net.alanproject.data.source.remote.RemoteSourceImpl
import net.alanproject.domain.common.BASE_URL
import net.alanproject.domain.repository.CartoonRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module @InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindRemoteSource(remoteSource: RemoteSourceImpl): RemoteSource

    @Binds
    abstract fun bindLocalSource(localSource: LocalSourceImpl): LocalSource

    @Binds
    abstract fun bindCartoonRepository(cartoonRepository: CartoonRepositoryImpl): CartoonRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideApi(): CartoonApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(CartoonApi::class.java)
    }

    @Provides
    fun provideFirebaseDatabase():DatabaseReference{
        return Firebase.database.reference
    }

    @Provides
    fun provideFirebaseRemoteConfig():FirebaseRemoteConfig{
        return FirebaseRemoteConfig.getInstance()
    }
}