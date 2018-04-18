package jonathansilva.com.freehub.search

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jonathansilva.com.freehub.github.ServiceFactory
import okhttp3.Headers
import java.util.*

class SearchPresenter(val view: SearchContract.View): SearchContract.Presenter {

    private var page = 1
    private var hasMore = false

    init {
        view.setPresenter(this)
    }

    override fun search(repoName: String) {
        // reset the page, it is a new search
        page = 1
        view.showLoading()
        // show data
        ServiceFactory.getService().search(repoName, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {

                    hasMore = hasMoreRepos(it.headers())
                    page += 1

                    view.hideLoading()
                    view.showData(it.body()?.items ?: Collections.emptyList())
                }
    }

    private fun hasMoreRepos(headers: Headers): Boolean {
        val linkHeader = headers.get("link")
        return if (linkHeader != null && linkHeader.contains("next")) true else false
    }

    override fun searchMore(repoName: String) {
        if (hasMore) {
            view.showLoading()
            ServiceFactory.getService()
                    .search(repoName, page)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe {

                        hasMore = hasMoreRepos(it.headers())
                        page += 1

                        view.hideLoading()
                        view.showMore(it.body()?.items ?: Collections.emptyList())
                    }
        }
    }
}