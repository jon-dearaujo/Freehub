package jonathansilva.com.freehub.repository

enum class SearchDataState {
    LOADING, /*Data is loading*/
    LOADED, /*loaded but more data may be available*/
    FINISHED /*It is done, last page loaded successfully*/
}