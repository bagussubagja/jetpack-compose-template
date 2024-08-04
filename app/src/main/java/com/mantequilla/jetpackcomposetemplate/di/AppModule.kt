package com.mantequilla.jetpackcomposetemplate.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.mantequilla.jetpackcomposetemplate.data.local.dao.GameFavDao
import com.mantequilla.jetpackcomposetemplate.data.local.AppDatabase
import com.mantequilla.jetpackcomposetemplate.data.repository.LocalRepository
import com.mantequilla.jetpackcomposetemplate.domain.repository.LocalRepositoryImpl
import com.mantequilla.jetpackcomposetemplate.data.remote.Api
import com.mantequilla.jetpackcomposetemplate.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        val chuckerCollector = ChuckerCollector(
            context = context,
            showNotification = true,
            RetentionManager.Period.FOREVER
        )

        val chuckerInterceptor = ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.GAME_TABLE
        ).build()
    }

    @Singleton
    @Provides
    fun provideGameApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    fun provideGameFav(appDatabase: AppDatabase) = appDatabase.dao

    @Provides
    fun provideGameFavRepository(gameFavDao: GameFavDao) : LocalRepository = LocalRepositoryImpl(gameFavDao)
}
