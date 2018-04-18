package jonathansilva.com.freehub.search

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jonathansilva.com.freehub.R

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val searchFragment = SearchFragment()

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentHolder, searchFragment)
                .commit()

        // Initialize the Presenter with a ref to the View
        SearchPresenter(searchFragment)
    }

}
