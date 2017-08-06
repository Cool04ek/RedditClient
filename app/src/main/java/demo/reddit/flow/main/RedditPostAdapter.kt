package demo.reddit.flow.main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.cell_post.view.*
import demo.reddit.R
import demo.reddit.data.model.RedditPost
import demo.reddit.util.load
import java.util.*

/**
 * Created by roma on 8/3/17.
 */
class RedditPostAdapter(val postClick: (RedditPost) -> Unit) : RecyclerView.Adapter<RedditPostAdapter.PostViewHolder>() {

    private val posts = ArrayList<RedditPost>()

    fun addPosts(postList: List<RedditPost>) {
        posts.addAll(postList)
        notifyDataSetChanged()
    }

    fun setPosts(postList: List<RedditPost>) {
        posts.clear()
        posts.addAll(postList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bindData()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PostViewHolder {
        return PostViewHolder(View.inflate(parent?.context, R.layout.cell_post, null))
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindData() {
            with(posts[adapterPosition]) {
                itemView.tvTitle.text = title
                itemView.tvComments.text = itemView.context.getString(R.string.comments_pattern, comments)
                itemView.ivPreview.load(preview, { requestCreator -> requestCreator.resizeDimen(R.dimen.image_size, R.dimen.image_size).centerInside() })

                itemView.setOnClickListener({ v -> postClick(this) })
            }
        }
    }

    fun getPosts(): List<RedditPost> {
        return Collections.unmodifiableList(posts)
    }
}