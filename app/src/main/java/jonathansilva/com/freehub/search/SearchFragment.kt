package jonathansilva.com.freehub.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import jonathansilva.com.freehub.R
import jonathansilva.com.freehub.github.GithubRepository
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.view_search_box.view.*

class SearchFragment: Fragment(), SearchContract.View {

    private lateinit var presenter: SearchContract.Presenter
    private lateinit var searchAdapter: SearchAdapter

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

        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && recyclerView?.canScrollVertically(RecyclerView.FOCUS_DOWN) == false) {
                    presenter.searchMore(searchBoxView.editText.text.toString())
                }
            }
        })
    }

    override fun setPresenter(presenter: SearchContract.Presenter) {
        this.presenter = presenter
    }

    override fun showData(repos: List<GithubRepository>) {
        searchAdapter = SearchAdapter(repos.toMutableList())

        val viewManager = LinearLayoutManager(activity)

        recyclerView.apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = searchAdapter
        }
    }

    override fun showMore(repos: List<GithubRepository>) {
        searchAdapter.data.addAll(repos)
        searchAdapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        searchBoxView.showLoading()
    }

    override fun hideLoading() {
        searchBoxView.hideLoading()
    }
}