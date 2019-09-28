package life.inaction.assignment.spacexdemo.di

import life.inaction.assignment.spacexdemo.api.API_VERSION
import life.inaction.assignment.spacexdemo.api.SPACEX_API
import life.inaction.assignment.spacexdemo.api.SpaceXService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val API_MODULE = module {
    // ok http client
    factory {
        OkHttpClient.Builder()
            .build()
    }

    // retrofit
    factory {
        Retrofit.Builder()
            .baseUrl("$SPACEX_API$API_VERSION/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    factory {
        val retrofit: Retrofit = get()
        retrofit.create(SpaceXService::class.java)
    }
}