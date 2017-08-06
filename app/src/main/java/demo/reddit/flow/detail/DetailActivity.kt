package demo.reddit.flow.detail

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.yalantis.api.ApiSettings
import demo.reddit.R
import demo.reddit.data.model.RedditPost
import demo.reddit.flow.main.PostDetailContract
import demo.reddit.flow.main.PostDetailPresenter
import kotlinx.android.synthetic.main.activity_detail.*
import java.lang.IllegalArgumentException

class DetailActivity : AppCompatActivity(), PostDetailContract.View {

    companion object {
        val REDDIT_POST = "reddit_post_key"
    }

    lateinit var post: RedditPost
    var optionsMenu: Menu? = null
    val presenter = PostDetailPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        presenter.attachView(this@DetailActivity)

        if (!intent.extras.containsKey(REDDIT_POST)) {
            throw IllegalArgumentException("Activity should has RedditPost as parameter")
        }
        post = intent.extras.get(REDDIT_POST) as RedditPost

        wvDetails.loadUrl(ApiSettings.SERVER + post.permalink)

        wvDetails.setWebViewClient(object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        optionsMenu = menu
        updateFavoriteStatus(presenter.isFavorite(post))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_favorite -> {
                post.favorite = !post.favorite
                if (post.favorite) {
                    presenter.addFavorite(post)
                } else {
                    presenter.removeFavorite(post)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun updateFavoriteStatus(favorite: Boolean) {
        post.favorite = favorite
        optionsMenu?.findItem(R.id.action_favorite)?.let {
            it.icon = resources.getDrawable(if (favorite) android.R.drawable.star_on
            else android.R.drawable.star_off, theme)
        }
    }

    override fun getContext(): Context {
        return this@DetailActivity
    }

    override fun showProgress() {
        //TODO("not implemented")
    }

    override fun hideProgress() {
        //TODO("not implemented")
    }

    override fun showError(strResId: Int) {
        //TODO("not implemented")
    }

    override fun showError(error: String?) {
        //TODO("not implemented")
    }
}
