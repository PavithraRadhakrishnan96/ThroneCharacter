package com.example.throneapp.characterlist.di

import androidx.room.Room
import com.example.throneapp.characterlist.MyApplication
import com.example.throneapp.characterlist.model.CharacterDao
import com.example.throneapp.characterlist.model.CharacterDatabase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Reusable
    internal fun provideHomeApi(retrofit: Retrofit): APIInterface {
        return retrofit.create(APIInterface::class.java)
    }

    @Provides
    @Reusable
    internal fun provideRetrofitInterface(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://thronesapi.com/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(okHttpClient)
            .build()
    }

    /**
     * Provides OkHttpClient to set the time out and logging
     * @return the OkHttpClient object
     */

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(interceptor)
        client.connectTimeout(20,TimeUnit.SECONDS)
        client.readTimeout(20, TimeUnit.SECONDS)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideDbInstance(): CharacterDatabase {
        return Room.databaseBuilder(MyApplication.instance, CharacterDatabase::class.java, "app_db")
            .allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun getUserDao(characterDatabase: CharacterDatabase):CharacterDao{
        return characterDatabase.getCharacterDAO()
    }
}