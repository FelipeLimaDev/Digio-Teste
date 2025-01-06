package br.com.digio.androidtest.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.ViewAssertion
import junit.framework.AssertionFailedError

fun hasItemCountAtLeast(minCount: Int): ViewAssertion {
    return ViewAssertion { view, noViewFoundException ->
        if (noViewFoundException != null) throw noViewFoundException

        val recyclerView = view as RecyclerView
        val itemCount = recyclerView.adapter?.itemCount ?: 0
        if (itemCount < minCount) {
            throw AssertionFailedError(
                "Esperado ao menos $minCount itens, mas o RecyclerView tem $itemCount"
            )
        }
    }
}