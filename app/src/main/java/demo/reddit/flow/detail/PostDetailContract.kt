package demo.reddit.flow.main

import demo.reddit.base.BasePresenter
import demo.reddit.base.BaseView
import demo.reddit.data.model.RedditPost

class PostDetailContract {

    internal interface Presenter : BasePresenter {

        fun addFavorite(post: RedditPost)

        fun removeFavorite(post: RedditPost)

        fun isFavorite(post: RedditPost): Boolean
    }

    interface View : BaseView {

        fun updateFavoriteStatus(favorite: Boolean)

    }

}
