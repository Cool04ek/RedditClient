package demo.reddit.data.source.reddit_post

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import demo.reddit.data.model.RedditChannelPage
import demo.reddit.data.source.base.BaseRemoteDataSource

/**
 * Created by roma on 8/3/17.
 */
class RedditPostRemoteDataSource : BaseRemoteDataSource() {

    fun getRedditPage(after: String): Single<RedditChannelPage> {
        return redditService.getPage(after)
                .flatMap { response -> Single.just(response.data) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}