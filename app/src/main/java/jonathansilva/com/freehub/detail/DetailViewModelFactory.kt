package jonathansilva.com.freehub.detail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import jonathansilva.com.freehub.api.APIFactory
import jonathansilva.com.freehub.repository.GithubRepository

class DetailViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(GithubRepository(APIFactory.getGithubAPI())) as T
        }

        throw IllegalArgumentException("Invalid ViewModel class ${modelClass.name}")
    }
}