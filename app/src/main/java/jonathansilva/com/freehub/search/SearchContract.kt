package jonathansilva.com.freehub.search

import io.reactivex.Observable
import jonathansilva.com.freehub.github.GithubRepository

interface SearchContract {
    interface View {
        fun setPresenter(presenter: Presenter)
        fun showLoading()
        fun hideLoading()
        fun showData(repos: List<GithubRepository>)
    }

    interface Presenter {
        fun search(repoName: String)
    }
}