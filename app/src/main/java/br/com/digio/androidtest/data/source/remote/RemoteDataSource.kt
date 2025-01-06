package br.com.digio.androidtest.data.source.remote

import br.com.digio.androidtest.data.dto.DigioProductsDto

class RemoteDataSource(private val apiService: DigioEndpoint) {

    suspend fun fetchDigioProducts(): DigioProductsDto {
        return apiService.getProducts()
    }
}