package br.com.digio.androidtest.data.source.remote

import br.com.digio.androidtest.data.dto.DigioProductsDto
import retrofit2.http.GET

interface DigioEndpoint {
    @GET("sandbox/products")
    suspend fun getProducts(): DigioProductsDto
}