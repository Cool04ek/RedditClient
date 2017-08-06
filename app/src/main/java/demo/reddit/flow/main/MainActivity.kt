package demo.reddit.flow.main

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.Toast
import demo.reddit.R
import demo.reddit.data.model.RedditPost
import demo.reddit.listeners.SearchListener
import demo.reddit.listeners.ViewPagerScrollListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), RedditChannelContract.View {

    companion object {
        val CHANNEL_POSITION = 0
        val FAVORITES_POSITION = 1
    }

    val channelFragment = ChannelFragment()
    val favoritesFragment = FavoritesFragment()
    val fragments = listOf(channelFragment, favoritesFragment)
    val presenter = RedditChannelPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        presenter.attachView(this)
        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }

            override fun getPageTitle(position: Int): CharSequence {
                when (position) {
                    CHANNEL_POSITION -> return this@MainActivity.getString(R.string.tab_top)
                    FAVORITES_POSITION -> return this@MainActivity.getString(R.string.tab_favorites)
                }
                return super.getPageTitle(position)
            }
        }
        viewPager.addOnPageChangeListener(ViewPagerScrollListener({ invalidateOptionsMenu() }))
    }

    override fun onResume() {
        super.onResume()
        presenter.fetchFavorites()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        if (viewPager.currentItem == CHANNEL_POSITION) {
            searchItem.isVisible = true
            searchView.setOnQueryTextListener(SearchListener({ query, all -> performSearch(query, all) }))
            searchView.setOnCloseListener { cancelSearch() }
        } else {
            searchItem.isVisible = false
            cancelSearch()
        }
        return true
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun fetchNextPage() {
        presenter.fetchNextPage()
    }

    override fun getContext(): Context {
        return this@MainActivity
    }

    override fun showProgress() {
        channelFragment.showProgress()
    }

    override fun hideProgress() {
        channelFragment.hideProgress()
    }

    override fun showError(strResId: Int) {
        Toast.makeText(this@MainActivity, strResId, Toast.LENGTH_SHORT).show()
    }

    override fun showError(error: String?) {
        Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
    }

    override fun showChannelPage(redditPosts: List<RedditPost>) {
        channelFragment.addPosts(redditPosts)
    }

    override fun showFavorites(redditPosts: List<RedditPost>) {
        favoritesFragment.setPosts(redditPosts)
    }

    fun performSearch(query: String, all: Boolean) {
        channelFragment.performSearch(query, all)
    }

    fun cancelSearch(): Boolean {
        channelFragment.cancelSearch()
        return false
    }
}
