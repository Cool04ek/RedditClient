package demo.reddit.base

import android.content.Context
import android.support.annotation.StringRes

interface BaseView {

    fun getContext(): Context

    fun showProgress()

    fun hideProgress()

    fun showError(@StringRes strResId: Int)

    fun showError(error: String?)

}
