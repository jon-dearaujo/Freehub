package jonathansilva.com.freehub.github

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    companion object {
        val BASE_URL = "https://api.github.com/search/"
    }

    @GET("repositories")
    fun search(@Query("q") name: String): Observable<SearchResponse>
}