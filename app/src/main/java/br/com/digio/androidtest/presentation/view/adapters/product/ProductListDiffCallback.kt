package br.com.digio.androidtest.presentation.view.adapters.product

import androidx.recyclerview.widget.DiffUtil
import br.com.digio.androidtest.domain.model.Product

class ProductListDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}
