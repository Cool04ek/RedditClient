package demo.reddit.listeners

import android.support.v7.widget.SearchView

/**
 * Created by roma on 8/6/17.
 */
class SearchListener(var performSearch: (query: String, all: Boolean) -> Unit)
    : SearchView.OnQueryTextListener {

    val minInput = 2
    val lastQuery: String = ""

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            if (query.length > minInput) {
                performSearch(query, decideSearch(query))
                return true
            }
        }
        return false

    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            if (newText.length > minInput) {
                performSearch(newText, decideSearch(newText))
            }
        }
        return true
    }

    fun decideSearch(query: String): Boolean {
        return lastQuery.isEmpty() || !query.contains(lastQuery, true)
    }
}