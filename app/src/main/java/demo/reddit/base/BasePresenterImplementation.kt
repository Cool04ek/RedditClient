package demo.reddit.base

import android.support.annotation.StringRes
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenterImplementation<V : BaseView> : BasePresenter {

    protected var view: V? = null
    private val disposables = CompositeDisposable()

    /**
     * Attach view to presenter, also here we have subscription
     * for destroy event. On destroy event we should detach view
     * and destroy presenter

     * @param view extend BaseView
     */
    @Suppress("UNCHECKED_CAST")
    override fun attachView(view: BaseView) {
        this.view = view as V
    }

    /**
     * This method adds given rx subscription to the [.subscriptionList]
     * which is unsubscribed [.detachView]

     * @param subscription - rx subscription that must be unsubscribed [.detachView]
     */
    protected fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    protected fun getString(@StringRes strResId: Int): String? {
        return view?.getContext()?.getString(strResId)
    }

    protected fun getString(@StringRes strResId: Int, vararg formatArgs: Any): String? {
        return view?.getContext()?.getString(strResId, *formatArgs)
    }

    /**
     * Here we are detaching view and removing and
     * unsubscribing all subscriptions
     */
    override fun detachView() {
        disposables.dispose()
        view = null
    }

}
