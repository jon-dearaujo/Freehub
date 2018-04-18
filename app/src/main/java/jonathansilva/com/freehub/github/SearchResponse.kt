package jonathansilva.com.freehub.github

class SearchResponse(val total_count: Int, val incomplete_results: Boolean, val items: List<GithubRepository>)