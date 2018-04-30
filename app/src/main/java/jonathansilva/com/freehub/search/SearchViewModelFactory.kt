package jonathansilva.com.freehub.search

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import jonathansilva.com.freehub.api.APIFactory
import jonathansilva.com.freehub.repository.GithubRepository

class SearchViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(GithubRepository(APIFactory.getGithubAPI())) as T
        }

        throw IllegalArgumentException("Invalid ViewModel class ${modelClass.name}")
    }
}