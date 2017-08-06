package demo.reddit.base

interface BasePresenter {

    fun attachView(view: BaseView)

    fun detachView()

}
