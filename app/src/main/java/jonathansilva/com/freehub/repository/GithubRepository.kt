package jonathansilva.com.freehub.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import jonathansilva.com.freehub.api.APIFactory
import jonathansilva.com.freehub.api.GithubAPI
import java.util.concurrent.Executors

class GithubRepository(private val api: GithubAPI) {
    val searchDataState: MutableLiveData<SearchDataState> = MutableLiveData()
    val searchErrorObservable: MutableLiveData<String> = MutableLiveData()

    fun search(query: String): LiveData<PagedList<GithubRepo>> =
            LivePagedListBuilder<Int, GithubRepo>(
                    DataSourceFactory(
                            api,
                            query,
                            searchDataState,
                            searchErrorObservable),
                    GithubAPI.PAGE_SIZE)
                    .setFetchExecutor(Executors.newFixedThreadPool(5))
                    .build()
}