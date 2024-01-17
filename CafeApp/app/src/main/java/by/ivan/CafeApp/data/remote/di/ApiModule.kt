package by.ivan.CafeApp.data.remote.di

import by.ivan.CafeApp.data.Constants
import by.ivan.CafeApp.ui.data.remote.util.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Singleton
    @Provides
    fun provideGson(): Gson { //todo ??
        return GsonBuilder().setLenient().create()
    }

    @Singleton
    @Provides
    fun provideApi(gson: Gson): ApiService {

        val okHttpClient = OkHttpClient.Builder()
            .also { client ->
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(logging)
            }.build()

        return Retrofit.Builder()
            .baseUrl(by.ivan.CafeApp.data.Constants.URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }
}