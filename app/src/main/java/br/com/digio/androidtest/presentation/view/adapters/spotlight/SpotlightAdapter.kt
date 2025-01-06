package br.com.digio.androidtest.presentation.view.adapters.spotlight

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import br.com.digio.androidtest.domain.model.Spotlight
import br.com.digio.androidtest.databinding.ItemMainSpotlightBinding

class SpotlightAdapter : ListAdapter<Spotlight, SpotlightItemViewHolder>(SpotlightDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotlightItemViewHolder {
        val binding = ItemMainSpotlightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpotlightItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpotlightItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}