package jonathansilva.com.freehub.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import jonathansilva.com.freehub.R
import jonathansilva.com.freehub.repository.SearchDataState
import jonathansilva.com.freehub.repository.GithubRepo

import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment: Fragment() {
    companion object {
        val TAG = "search_fragment";
    }

    private val dataObserver = Observer<PagedList<GithubRepo>> {showData(it)}
    private val stateObserver = Observer<SearchDataState> {handleStateChange(it)}
    private val errorObserver = Observer<String> {
        it?.let {
            hideLoading()
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        }
    }

    lateinit var navigator: SearchNavigator
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
            inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders
                .of(this, SearchViewModelFactory())
                .get(SearchViewModel::class.java)

        viewModel.searchData.observe(this, dataObserver)
        viewModel.dataState.observe(this, stateObserver)
        viewModel.errorObserver.observe(this, errorObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchAdapter = SearchAdapter(navigator)

        val viewManager = LinearLayoutManager(activity?.applicationContext)

        recyclerView.apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = searchAdapter
        }

        searchBoxView
                .onSearch()
                .subscribe {
                    viewModel.load(it)
                }

    }

    fun showData(data: PagedList<GithubRepo>?) {
        data?.let {
            searchAdapter.submitList(it)
        }
    }

    fun handleStateChange(state: SearchDataState?) {
        state?.let {
            when (it) {
                SearchDataState.LOADED -> hideLoading()
                SearchDataState.LOADING -> showLoading()
                SearchDataState.FINISHED -> {
                    hideLoading()
                    Toast.makeText(activity, "All repositories are loaded", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun showLoading() {
        searchBoxView.showLoading()
    }

    fun hideLoading() {
        searchBoxView.hideLoading()
    }
}