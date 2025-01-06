package br.com.digio.androidtest.domain.repository

import br.com.digio.androidtest.domain.utils.DataError
import br.com.digio.androidtest.domain.model.DigioProducts
import br.com.digio.androidtest.domain.utils.EmptyResult
import br.com.digio.androidtest.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun fetchDigioProducts(): Result<DigioProducts, DataError>
    suspend fun saveDigioProducts(digioProducts: DigioProducts) :EmptyResult<DataError.Local>
    fun getDigioProducts(): Flow<DigioProducts?>
}