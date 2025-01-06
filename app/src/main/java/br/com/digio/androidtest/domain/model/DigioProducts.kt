package br.com.digio.androidtest.domain.model

data class DigioProducts(
    val cash: Cash,
    val products: List<Product>,
    val spotlights: List<Spotlight>
)
