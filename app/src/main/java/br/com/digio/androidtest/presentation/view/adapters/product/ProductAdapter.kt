package br.com.digio.androidtest.presentation.view.adapters.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import br.com.digio.androidtest.databinding.ItemMainProductsBinding
import br.com.digio.androidtest.domain.model.Product


class ProductAdapter : ListAdapter<Product, ProductItemViewHolder>(ProductListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        val binding =
            ItemMainProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}