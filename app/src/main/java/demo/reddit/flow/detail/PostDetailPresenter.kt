package demo.reddit.flow.main

import demo.reddit.base.BasePresenterImplementation
import demo.reddit.data.model.RedditPost
import demo.reddit.data.source.reddit_post.RedditPostRepository

/**
 * Created by roma on 8/3/17.
 */
class PostDetailPresenter : BasePresenterImplementation<PostDetailContract.View>(), PostDetailContract.Presenter {

    private val redditPostRepository = RedditPostRepository()

    override fun addFavorite(post: RedditPost) {
        redditPostRepository.addFavoritePost(post)
        view?.updateFavoriteStatus(true)
    }

    override fun removeFavorite(post: RedditPost) {
        redditPostRepository.removeFavoritePost(post)
        view?.updateFavoriteStatus(false)
    }

    override fun isFavorite(post: RedditPost): Boolean {
        return redditPostRepository.isFavorite(post)
    }
}