package jonathansilva.com.freehub.search

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jonathansilva.com.freehub.github.GithubRepository
import jonathansilva.com.freehub.github.ServiceFactory

class SearchPresenter(val view: SearchContract.View): SearchContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun search(repoName: String) {
        view.showLoading()
        // show data
        val searchResult = ServiceFactory.getService().search(repoName)

        searchResult
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    view.hideLoading()
                    view.showData(it.items)
                }
    }
}