package br.com.digio.androidtest.data.source.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.digio.androidtest.domain.model.Cash
import br.com.digio.androidtest.domain.model.Product
import br.com.digio.androidtest.domain.model.Spotlight

@Entity(tableName = "DigioProducts")
data class DigioProductsEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val cash: Cash,
    val products: List<Product>,
    val spotlights: List<Spotlight>
)


