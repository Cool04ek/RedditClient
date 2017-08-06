package demo.reddit.flow.main

import demo.reddit.base.BasePresenter
import demo.reddit.base.BaseView
import demo.reddit.data.model.RedditPost

class RedditChannelContract {

    internal interface Presenter : BasePresenter {

        fun fetchNextPage()

        fun fetchFavorites()
    }

    interface View : BaseView {

        fun fetchNextPage()

        fun showChannelPage(redditPosts: List<RedditPost>)

        fun showFavorites(redditPosts: List<RedditPost>)
    }

}
