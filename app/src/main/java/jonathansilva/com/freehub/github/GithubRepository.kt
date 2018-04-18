package jonathansilva.com.freehub.github

data class GithubRepository(
        val name: String,
        val description: String?,
        val full_name: String,
        val owner: GithubUser,
        val html_url: String,
        val language: String,
        val url: String,
        val stargazers_count: Int)