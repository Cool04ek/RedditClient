package demo.reddit.api.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import demo.reddit.data.model.RedditPost
import java.lang.reflect.Type
import java.net.URLDecoder

class RedditPostDeserializer : JsonDeserializer<RedditPost> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): RedditPost? {
        val data = json.asJsonObject.get("data")
        val id = data.asJsonObject.get("id").asString
        val title = data.asJsonObject.get("title").asString
        val permalink = data.asJsonObject.get("permalink").asString
        val comments = data.asJsonObject.get("num_comments").asInt
        val preview: String? = data.asJsonObject?.getAsJsonObject("preview")?.getAsJsonArray("images")
                ?.get(0)?.asJsonObject?.getAsJsonObject("source")?.get("url")?.asString?.let {
            URLDecoder.decode(it, "UTF-8")
        }
        return RedditPost(id, comments, title, permalink, preview)
    }

}
