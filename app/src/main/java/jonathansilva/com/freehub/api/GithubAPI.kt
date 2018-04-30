package jonathansilva.com.freehub.api

import io.reactivex.Observable
import jonathansilva.com.freehub.repository.SearchResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubAPI {
    companion object {
        val PAGE_SIZE: Int = 20
    }

    @GET("repositories")
    fun search(
            @Query("q") name: String,
            @Query("page") page: Int,
            @Query("sort") sort: String = "stars",
            @Query("per_page")pageSize: Int = PAGE_SIZE): Call<SearchResponse>
}