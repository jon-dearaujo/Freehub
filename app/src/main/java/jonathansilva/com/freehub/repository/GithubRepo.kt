package jonathansilva.com.freehub.repository

import com.google.gson.annotations.SerializedName

data class GithubRepo(
        val id: Int,
        val name: String,
        val description: String?,
        @SerializedName("full_name")val fullName: String,
        val owner: GithubUser,
        @SerializedName("html_url") val webUrl: String,
        val language: String,
        @SerializedName("url") val apiUrl: String,
        @SerializedName("stargazers_count") val stars: Int)