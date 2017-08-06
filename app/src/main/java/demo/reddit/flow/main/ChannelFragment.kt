package demo.reddit.flow.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_posts_list.*
import demo.reddit.R
import demo.reddit.data.model.RedditPost
import demo.reddit.flow.detail.DetailActivity
import demo.reddit.listeners.EndlessScrollListener


/**
 * Created by roma on 8/3/17.
 */
class ChannelFragment : Fragment() {

    private lateinit var topView: RedditChannelContract.View
    private lateinit var linearLayout: LinearLayoutManager

    private val adapter = RedditPostAdapter({ redditPost -> openPostDetails(redditPost) })
    private val searchAdapter = RedditPostAdapter({ redditPost -> openPostDetails(redditPost) })

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (activity is RedditChannelContract.View) {
            topView = activity as RedditChannelContract.View
        } else {
            throw IllegalArgumentException("Fragment should belong to RedditChannelContract")
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View.inflate(inflater?.context, R.layout.fragment_posts_list, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linearLayout = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvPosts.layoutManager = linearLayout
        rvPosts.adapter = adapter
        rvPosts.addOnScrollListener(EndlessScrollListener(linearLayout, { topView.fetchNextPage() }))
        topView.fetchNextPage()

        rvSearchResults.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvSearchResults.adapter = searchAdapter
    }

    fun addPosts(posts: List<RedditPost>) {
        adapter.addPosts(posts)
    }

    fun showProgress() {
        progressBar?.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progressBar?.visibility = View.GONE
    }

    fun performSearch(query: String, all: Boolean) {
        if (rvSearchResults.visibility == View.GONE) {
            rvPosts.visibility = View.INVISIBLE
            rvSearchResults.visibility = View.VISIBLE
        }

        val searchResults = ArrayList<RedditPost>()
        if (all) {
            adapter.getPosts().filterTo(searchResults) { it.title.contains(query, true) }
        } else {
            searchAdapter.getPosts().filterTo(searchResults) { it.title.contains(query, true) }
        }
        searchAdapter.setPosts(searchResults)
    }

    fun cancelSearch() {
        searchAdapter.setPosts(emptyList())
        rvPosts.visibility = View.VISIBLE
        rvSearchResults.visibility = View.GONE
    }

    fun openPostDetails(post: RedditPost) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.REDDIT_POST, post)
        startActivity(intent)
    }
}