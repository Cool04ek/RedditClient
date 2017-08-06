package demo.reddit.data.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by roma on 8/3/17.
 */
class RedditPost(var id: String, var comments: Int, var title: String, var permalink: String, var preview: String?, var favorite: Boolean = false) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(comments)
        parcel.writeString(title)
        parcel.writeString(permalink)
        parcel.writeString(preview)
        parcel.writeByte(if (favorite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RedditPost> {
        override fun createFromParcel(parcel: Parcel): RedditPost {
            return RedditPost(parcel)
        }

        override fun newArray(size: Int): Array<RedditPost?> {
            return arrayOfNulls(size)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is RedditPost) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}