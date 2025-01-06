package br.com.digio.androidtest.presentation.view.adapters.spotlight

import androidx.recyclerview.widget.DiffUtil
import br.com.digio.androidtest.domain.model.Spotlight

class SpotlightDiffCallback : DiffUtil.ItemCallback<Spotlight>() {
    override fun areItemsTheSame(oldItem: Spotlight, newItem: Spotlight): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Spotlight, newItem: Spotlight): Boolean {
        return oldItem == newItem
    }
}