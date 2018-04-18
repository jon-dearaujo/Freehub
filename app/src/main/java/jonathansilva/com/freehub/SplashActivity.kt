package jonathansilva.com.freehub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jonathansilva.com.freehub.search.SearchActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(applicationContext, SearchActivity::class.java)
        startActivity(intent)
    }
}
