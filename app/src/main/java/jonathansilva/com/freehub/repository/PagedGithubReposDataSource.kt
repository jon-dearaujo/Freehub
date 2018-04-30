package jonathansilva.com.freehub.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.google.gson.Gson
import jonathansilva.com.freehub.api.GithubAPI
import okhttp3.Headers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class PagedGithubReposDataSource(
        val api: GithubAPI,
        val repoName: String,
        val dataStateChangeObservable: MutableLiveData<SearchDataState>,
        val errorObservable: MutableLiveData<String>): PageKeyedDataSource<Int, GithubRepo>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, GithubRepo>) {
        dataStateChangeObservable.postValue(SearchDataState.LOADING)
        api.search(repoName, 1)
                .enqueue(object : Callback<SearchResponse> {
                    override fun onFailure(call: Call<SearchResponse>, t: Throwable?) {
                        Log.d(javaClass.name, "Some problems while loading data", t)
                        dataStateChangeObservable.postValue(SearchDataState.LOADED)
                    }

                    override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                        if (response.isSuccessful) {
                            val items = response.body()?.items ?: Collections.emptyList()
                            val nextPage = if (hasMoreRepos(response.headers())) 2 else null
                            callback.onResult(items, null, nextPage)
                            if (nextPage != null) {
                                dataStateChangeObservable.postValue(SearchDataState.LOADED)
                            } else {
                                dataStateChangeObservable.postValue(SearchDataState.FINISHED)
                            }
                        } else {
                            handleError(response)
                        }
                    }

                })
    }

    private fun handleError(response: Response<SearchResponse>) {
        response.errorBody()?.let {
            dataStateChangeObservable.postValue(SearchDataState.LOADED)
            val errorBody: Map<String, String> =
                    Gson().fromJson(it.string(), Map::class.java) as Map<String, String>
            errorObservable.postValue(errorBody["message"])
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GithubRepo>) {
        dataStateChangeObservable.postValue(SearchDataState.LOADING)
        api.search(repoName, params.key)
                .enqueue(object: Callback<SearchResponse> {
                    override fun onFailure(call: Call<SearchResponse>, t: Throwable?) {
                        Log.d(javaClass.name, "Some problems while loading data", t)
                        dataStateChangeObservable.postValue(SearchDataState.LOADED)
                    }

                    override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                        if (response.isSuccessful) {
                            val items = response.body()?.items ?: Collections.emptyList()
                            val nextPage = if (hasMoreRepos(response.headers())) params.key + 1 else null
                            callback.onResult(items, nextPage)
                            if (nextPage != null) {
                                dataStateChangeObservable.postValue(SearchDataState.LOADED)
                            } else {
                                dataStateChangeObservable.postValue(SearchDataState.FINISHED)
                            }
                        } else {
                            handleError(response)
                        }
                    }
                })

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GithubRepo>) {
        /*No needed, we only append new data to the initial request*/
    }

    private fun hasMoreRepos(headers: Headers): Boolean {
        val linkHeader = headers.get("link")
        return linkHeader != null && linkHeader.contains("next")
    }
}