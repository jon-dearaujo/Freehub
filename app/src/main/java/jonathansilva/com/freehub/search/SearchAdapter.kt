package jonathansilva.com.freehub.search

import android.arch.paging.PagedListAdapter
import android.support.constraint.ConstraintLayout
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import jonathansilva.com.freehub.R
import jonathansilva.com.freehub.repository.GithubRepo

class SearchAdapter(private val searchNavigator: SearchNavigator)
    : PagedListAdapter<GithubRepo, SearchAdapter.ViewHolder>(GithubRepoDiff()) {

    class ViewHolder(val view: ConstraintLayout): RecyclerView.ViewHolder(view) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_repo_item, parent, false) as ConstraintLayout
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = getItem(position)
        repo?.let {r ->
            Picasso.get().load(r.owner.avatarUrl)
                    .into(holder.view.findViewById<ImageView>(R.id.repoAvatar))
            holder.view.findViewById<TextView>(R.id.repoName).text = r.name
            holder.view.findViewById<TextView>(R.id.repoDescription).text = r.description
            holder.view.findViewById<TextView>(R.id.language).text = r.language
            holder.view.findViewById<TextView>(R.id.stars).text = r.stars.toString()
            holder.itemView.setOnClickListener {onItemClick(r)}
        }

    }

    private fun onItemClick(repo: GithubRepo) {
        searchNavigator.openDetailFragment(repo)
    }

    class GithubRepoDiff: DiffUtil.ItemCallback<GithubRepo>(){
        override fun areItemsTheSame(oldItem: GithubRepo?, newItem: GithubRepo?): Boolean {
            return oldItem?.id == newItem?.id
        }

        override fun areContentsTheSame(oldItem: GithubRepo?, newItem: GithubRepo?): Boolean {
            return oldItem?.equals(newItem)!!
        }

    }
}