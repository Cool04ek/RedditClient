package demo.reddit.flow.main

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


/**
 * Created by roma on 8/3/17.
 */
class FavoritesFragment : Fragment() {

    private val adapter = RedditPostAdapter({ redditPost ->
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.REDDIT_POST, redditPost)
        startActivity(intent)
    })

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View.inflate(inflater?.context, R.layout.fragment_posts_list, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvPosts.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvPosts.adapter = adapter
    }

    fun setPosts(postsList: List<RedditPost>) {
        adapter.setPosts(postsList)
    }
}