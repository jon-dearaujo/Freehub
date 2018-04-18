package jonathansilva.com.freehub.search

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import jonathansilva.com.freehub.R
import jonathansilva.com.freehub.github.GithubRepository

class SearchAdapter(val data: List<GithubRepository>): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    class ViewHolder(val view: ConstraintLayout): RecyclerView.ViewHolder(view) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_repo_item, parent, false) as ConstraintLayout
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = data[position]
        Picasso.get().load(repo.owner.avatar_url)
                .into(holder.view.findViewById<ImageView>(R.id.repoAvatar))
        holder.view.findViewById<TextView>(R.id.repoName).text = repo.name
        holder.view.findViewById<TextView>(R.id.repoDescription)
                .text = prepareDescription(repo.description)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun prepareDescription(description: String?): String? {
        return if (description != null && description?.length > 50)
            description.substring(0, 50).plus("...")
        else description
    }
}