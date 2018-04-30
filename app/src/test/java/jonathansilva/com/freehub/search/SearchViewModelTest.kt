package jonathansilva.com.freehub.search

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import jonathansilva.com.freehub.repository.GithubRepository
import jonathansilva.com.freehub.repository.SearchDataState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class SearchViewModelTest {

    val SEARCH_STRING = "Repo"

    lateinit var repository: GithubRepository

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun warmUp() {
        repository = Mockito.mock(GithubRepository::class.java)
        Mockito.`when`(repository.searchErrorObservable)
                .thenReturn(MutableLiveData())
        Mockito.`when`(repository.searchDataState)
                .thenReturn(MutableLiveData())
    }

    @Test
    fun itShouldInteractWithRepository_SearchMethodWhenLoadIsCalled() {
        val viewModel = SearchViewModel(repository)

        viewModel.load(SEARCH_STRING)
        viewModel.searchData.observeForever({})

        Mockito.verify(repository).search(SEARCH_STRING)
    }

    @Test
    fun itShouldStartWithLOADEDDataState() {
        val viewModel = SearchViewModel(repository)

        viewModel.dataState.observeForever {
            it == SearchDataState.LOADED
        }
    }
}