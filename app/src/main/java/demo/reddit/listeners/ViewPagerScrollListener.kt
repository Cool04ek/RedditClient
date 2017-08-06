package demo.reddit.listeners

import android.support.v4.view.ViewPager

/**
 * Created by roma on 8/6/17.
 */
class ViewPagerScrollListener(var pageSelected: () -> Unit) : ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {
        //empty for this case
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        //empty for this case
    }

    override fun onPageSelected(position: Int) {
        pageSelected()
    }
}