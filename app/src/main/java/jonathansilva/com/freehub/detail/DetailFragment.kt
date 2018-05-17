package jonathansilva.com.freehub.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import jonathansilva.com.freehub.R
import jonathansilva.com.freehub.repository.GithubRepo
import jonathansilva.com.freehub.repository.LoadDataState

import kotlinx.android.synthetic.main.fragment_detail.*
import java.lang.Exception

class DetailFragment: Fragment() {

    companion object {
        val TAG = "detail_fragment";
    }

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)



        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders
                .of(this, DetailViewModelFactory())
                .get(DetailViewModel::class.java)

        val apiUrl = arguments?.getString("url")
        apiUrl?.takeUnless { it.isNullOrEmpty() }?.let {
            viewModel.load(apiUrl)
        }

        viewModel.repoLiveData.observe(this, Observer<GithubRepo> {
            it?.let {
                bind(it)
            }
        })

        viewModel.loadState.observe(this, Observer<LoadDataState> {
          it?.let {
              when(it) {
                  LoadDataState.LOADED -> {
                      loadProgressBar.visibility = View.GONE
                      loadProgressBar.elevation = 0f

                      detailView.visibility = View.VISIBLE
                      detailView.elevation = 1f
                  }
                  LoadDataState.LOADING -> {
                      detailView.visibility = View.GONE
                      detailView.elevation = 0f

                      loadProgressBar.visibility = View.VISIBLE
                      loadProgressBar.elevation = 1f
                  }
              }
          }
        })
    }

    private fun bind(repo: GithubRepo) {
        Picasso.get()
                .load(repo.owner.avatarUrl)
                .into(PicassoImageLoadTarget(avatarFrameLayout, context))

        nameTextView.text = repo.name
        starsTextView.text = repo.stars.toString()
        forksTextView.text = repo.forks.toString()
        watchesTextView.text = repo.watchers.toString()
        issuesTextView.text = repo.issues.toString()
        isForkImageView.visibility = if (repo.isFork) View.VISIBLE else View.GONE
    }
}