package demo.reddit.api.services

import com.yalantis.api.ApiSettings
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import demo.reddit.api.body.RedditResponse
import demo.reddit.data.model.RedditChannelPage

interface RedditService {

    @GET(ApiSettings.PROGRAMMER_HUMOR_POSTS)
    fun getPage(@Query(ApiSettings.POST_AFTER) afterLink: String): Single<RedditResponse<RedditChannelPage>>

}