package jonathansilva.com.freehub.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import jonathansilva.com.freehub.R
import jonathansilva.com.freehub.github.GithubRepository
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment: Fragment(), SearchContract.View {

    private lateinit var presenter: SearchContract.Presenter

    override fun onCreateView(
            inflater: LayoutInflater?,container: ViewGroup?, savedInstanceState: Bundle?): View? {
         return inflater!!.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        searchBoxView
                .onSearch()
                .subscribe {
                    presenter.search(it)
                }
    }

    override fun setPresenter(presenter: SearchContract.Presenter) {
        this.presenter = presenter
    }

    override fun showData(repos: List<GithubRepository>) {
        val searchAdapter = SearchAdapter(repos)

        val viewManager = LinearLayoutManager(activity)

        recyclerView.apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = searchAdapter
        }
    }

    override fun showLoading() {
        searchBoxView.showLoading()
    }

    override fun hideLoading() {
        searchBoxView.hideLoading()
    }
}