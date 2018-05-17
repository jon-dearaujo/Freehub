package jonathansilva.com.freehub.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import jonathansilva.com.freehub.repository.GithubRepo
import jonathansilva.com.freehub.repository.GithubRepository

class DetailViewModel(private val githubRepository: GithubRepository): ViewModel() {
    private val apiUrlLiveData = MutableLiveData<String>()
    val repoLiveData: LiveData<GithubRepo> = Transformations.switchMap(apiUrlLiveData, {
        githubRepository.load(it)
    })
    val loadState = githubRepository.loadDataState

    fun load(apiUrl: String) {
        apiUrlLiveData.value = apiUrl
    }
}