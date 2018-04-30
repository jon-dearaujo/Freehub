package jonathansilva.com.freehub.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import jonathansilva.com.freehub.api.GithubAPI

class DataSourceFactory(
        val api: GithubAPI,
        val repoName: String,
        val searchDataState: MutableLiveData<SearchDataState>,
        val errorObservable: MutableLiveData<String>): DataSource.Factory<Int, GithubRepo>() {

    override fun create(): DataSource<Int, GithubRepo> {
        return PagedGithubReposDataSource(api, repoName, searchDataState, errorObservable)
    }

}