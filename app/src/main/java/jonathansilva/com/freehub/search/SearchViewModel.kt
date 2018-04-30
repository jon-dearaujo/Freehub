package jonathansilva.com.freehub.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import jonathansilva.com.freehub.repository.*

class SearchViewModel(private val githubRepository: GithubRepository): ViewModel() {

    val searchData: LiveData<PagedList<GithubRepo>>
    val dataState: MutableLiveData<SearchDataState> = githubRepository.searchDataState
    val errorObserver: MutableLiveData<String> = githubRepository.searchErrorObservable

    private val liveQuery = MutableLiveData<String>()

    init {
        /*Uses Transformations to map values of search string managed by the liveQuery
        into search responses*/
        searchData = Transformations.switchMap(liveQuery, {
            githubRepository.search(it)
        })
        dataState.value = SearchDataState.LOADED
    }

    fun load(query: String) {
        liveQuery.value = query
    }
}