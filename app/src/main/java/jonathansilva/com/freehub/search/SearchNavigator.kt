package jonathansilva.com.freehub.search

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import jonathansilva.com.freehub.R
import jonathansilva.com.freehub.detail.DetailFragment
import jonathansilva.com.freehub.repository.GithubRepo

class SearchNavigator(val fragmentManager: FragmentManager) {
    fun openDetailFragment(repo: GithubRepo) {
        var fragment: Fragment? = fragmentManager.findFragmentByTag(DetailFragment.TAG)
        fragment = fragment ?: DetailFragment()
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentHolder, fragment, DetailFragment.TAG)
                .addToBackStack(null)
                .commit()
    }

    fun openSearchFragment() {
        var fragment: Fragment? = fragmentManager.findFragmentByTag(SearchFragment.TAG)
        fragment = (fragment ?: SearchFragment()) as SearchFragment

        fragment.let {
            it.navigator = this
        }

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentHolder, fragment, SearchFragment.TAG)
                .commit()
    }
}