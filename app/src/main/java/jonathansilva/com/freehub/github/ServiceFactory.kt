package jonathansilva.com.freehub.github

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ServiceFactory {
    companion object {
        fun getService(): GithubService {
            return Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(GithubService.BASE_URL)
                    .build()
                    .create(GithubService::class.java)
        }
    }
}