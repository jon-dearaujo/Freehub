package jonathansilva.com.freehub.repository

import com.google.gson.annotations.SerializedName
import java.util.*

data class SearchResponse(
        @SerializedName("total_count") val total: Int = 0,
        val hasMore: Boolean = false,
        val items: List<GithubRepo> = Collections.emptyList())