package demo.reddit.util

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

/**
 * Created by roma on 8/3/17.
 */

val Context.picasso: Picasso
    get() = Picasso.with(this)

fun ImageView.load(path: String?, request: (RequestCreator) -> RequestCreator) {
    request(context.picasso.load(path)).into(this)
}