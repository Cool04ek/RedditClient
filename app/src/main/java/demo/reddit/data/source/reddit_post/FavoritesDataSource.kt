package demo.reddit.data.source.reddit_post

import demo.reddit.data.model.RedditPost
import demo.reddit.data.source.base.BaseDataSource

/**
 * Created by roma on 8/3/17.
 */
object FavoritesDataSource : BaseDataSource {

    private var favoritesSet = HashSet<RedditPost>()

    override fun init() {
        //empty
    }

    override fun clear() {
        favoritesSet.clear()
    }

    fun addFavorite(post: RedditPost) {
        favoritesSet.add(post)
    }

    fun removeFavorite(post: RedditPost) {
        favoritesSet.remove(post)
    }

    fun getFavorites(): HashSet<RedditPost> {
        return favoritesSet
    }

    fun isFavorite(post: RedditPost): Boolean {
        return favoritesSet.contains(post)
    }
}