package jonathansilva.com.freehub.search

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jonathansilva.com.freehub.R
import jonathansilva.com.freehub.detail.DetailFragment

class SearchActivity : AppCompatActivity() {

    private var navigator: SearchNavigator = SearchNavigator(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        navigator.openSearchFragment()
    }

    override fun onBackPressed() {
        if (isDetailFragmentVisible()) {
            super.onBackPressed()
        } else {
            // Do nothing, otherwise it is going back to Splash activity
        }
    }

    private fun isDetailFragmentVisible(): Boolean {
        val detailFragment = supportFragmentManager.findFragmentByTag(DetailFragment.TAG)
        return detailFragment != null && detailFragment.isVisible
    }
}
