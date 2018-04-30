package jonathansilva.com.freehub.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class APIFactory {
    companion object {
        val BASE_URL = "https://api.github.com/search/"
        fun getGithubAPI(): GithubAPI {
            return Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
                    .create(GithubAPI::class.java)
        }
    }
}