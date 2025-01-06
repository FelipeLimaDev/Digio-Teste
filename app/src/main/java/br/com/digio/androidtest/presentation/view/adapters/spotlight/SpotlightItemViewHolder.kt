package br.com.digio.androidtest.presentation.view.adapters.spotlight

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.digio.androidtest.R
import br.com.digio.androidtest.domain.model.Spotlight
import br.com.digio.androidtest.databinding.ItemMainSpotlightBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class SpotlightItemViewHolder(binding: ItemMainSpotlightBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val imageView = binding.imgMainItem
    private val progress = binding.progressImage

    fun bind(product: Spotlight) {

        imageView.contentDescription = product.name
        progress.visibility = View.VISIBLE

        Picasso.get()
            .load(product.bannerURL)
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

