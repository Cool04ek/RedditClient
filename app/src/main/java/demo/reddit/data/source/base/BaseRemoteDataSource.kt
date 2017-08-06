package demo.reddit.data.source.base

import com.google.gson.GsonBuilder
import com.yalantis.api.ApiSettings
import demo.reddit.api.deserializer.RedditPostDeserializer
import demo.reddit.api.services.RedditService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseRemoteDataSource : BaseDataSource {

    protected lateinit var redditService: RedditService
    private lateinit var retrofit: Retrofit

    override fun init() {
        initRetrofit()
        initServices()
    }

    private fun initServices() {
        redditService = retrofit.create<RedditService>(RedditService::class.java)
    }

    private fun initRetrofit() {
        val level = HttpLoggingInterceptor.Level.BODY //as far as we have only test version I provide logs for network requests
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                    .method(original.method(), original.body())
                    .build()
            chain.proceed(request)
        }.addInterceptor(HttpLoggingInterceptor().setLevel(level)).build()

        retrofit = Retrofit.Builder()
                .baseUrl(ApiSettings.SERVER)
                .addConverterFactory(createGsonConverter())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
    }

    private fun createGsonConverter(): GsonConverterFactory {
        val builder = GsonBuilder()
        builder.serializeNulls()
        builder.registerTypeAdapter(Class.forName("demo.reddit.data.model.RedditPost"), RedditPostDeserializer())
        return GsonConverterFactory.create(builder.create())
    }

    override fun clear() {

    }

}
