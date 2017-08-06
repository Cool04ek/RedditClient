package demo.reddit.data.source.reddit_post

import io.reactivex.Single
import demo.reddit.data.model.RedditChannelPage
import demo.reddit.data.model.RedditPost

/**
 * Created by roma on 8/3/17.
 */
class RedditPostRepository {

    private val favoritesDataSource = FavoritesDataSource

    private val redditRemoteDataSource: RedditPostRemoteDataSource = RedditPostRemoteDataSource().apply {
        init()
    }

    fun getRedditPage(after: String): Single<RedditChannelPage> {
        return redditRemoteDataSource.getRedditPage(after)
    }

    fun addFavoritePost(post: RedditPost) {
        favoritesDataSource.addFavorite(post)
    }

    fun removeFavoritePost(post: RedditPost) {
        favoritesDataSource.removeFavorite(post)
    }

    fun getFavorites(): HashSet<RedditPost> {
        return favoritesDataSource.getFavorites()
    }

    fun isFavorite(post: RedditPost): Boolean {
        return favoritesDataSource.isFavorite(post)
    }

}