package demo.reddit.flow.main

import android.util.Log
import demo.reddit.base.BasePresenterImplementation
import demo.reddit.data.model.RedditChannelPage
import demo.reddit.data.model.RedditPost
import demo.reddit.data.source.reddit_post.RedditPostRepository

/**
 * Created by roma on 8/3/17.
 */
class RedditChannelPresenter : BasePresenterImplementation<RedditChannelContract.View>(), RedditChannelContract.Presenter {

    private val redditPostRepository = RedditPostRepository()
    private var currentPage: RedditChannelPage? = null

    override fun fetchNextPage() {
        view?.showProgress()
        addDisposable(redditPostRepository.getRedditPage(currentPage?.after ?: "").subscribe({ page ->
            currentPage = page
            view?.showChannelPage(page.children)
            view?.hideProgress()
        }, { error ->
            view?.showError(error.localizedMessage)
            view?.hideProgress()
            Log.i("TAG", error.localizedMessage)
        }))
    }

    override fun fetchFavorites() {
        view?.showFavorites(ArrayList<RedditPost>(redditPostRepository.getFavorites()))
    }
}