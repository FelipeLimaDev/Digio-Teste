package br.com.digio.androidtest.presentation.view.adapters.product

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.digio.androidtest.R
import br.com.digio.androidtest.databinding.ItemMainProductsBinding
import br.com.digio.androidtest.domain.model.Product
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class ProductItemViewHolder(binding: ItemMainProductsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val imageView = binding.imgMainItem
    private val progress = binding.progressImage

    fun bind(product: Product) {

        imageView.contentDescription = product.name
        progress.visibility = View.VISIBLE

        Picasso.get()
            .load(product.imageURL)
            .error(R.drawable.ic_alert_circle)
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    progress.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    progress.visibility = View.GONE
                }
            })
    }
}
