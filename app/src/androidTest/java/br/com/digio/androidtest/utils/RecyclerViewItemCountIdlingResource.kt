package br.com.digio.androidtest.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.IdlingResource

/**
 * Um IdlingResource que fica "ativo" até que a RecyclerView tenha pelo menos [minItemCount] itens.
 */
class RecyclerViewItemCountIdlingResource(
    private val recyclerView: RecyclerView,
    private val minItemCount: Int = 1
) : IdlingResource {

    @Volatile
    private var callback: IdlingResource.ResourceCallback? = null

    override fun getName(): String {
        return "RecyclerViewItemCountIdlingResource(minItemCount=$minItemCount)"
    }

    override fun isIdleNow(): Boolean {
        val adapter = recyclerView.adapter
        val isIdle = if (adapter != null) {
            adapter.itemCount >= minItemCount
        } else {
            false
        }

        if (isIdle) {
            callback?.onTransitionToIdle()
        }
        return isIdle
    }

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback) {
        this.callback = resourceCallback
    }
}
