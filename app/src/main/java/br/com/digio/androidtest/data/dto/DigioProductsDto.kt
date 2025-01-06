package br.com.digio.androidtest.data.dto

data class DigioProductsDto(
    val cash: CashDto? = null,
    val products: List<ProductDto>? = null,
    val spotlight: List<SpotlightDto>? = null
)