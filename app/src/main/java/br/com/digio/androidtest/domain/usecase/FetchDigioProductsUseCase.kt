package br.com.digio.androidtest.domain.usecase

import br.com.digio.androidtest.domain.model.DigioProducts
import br.com.digio.androidtest.domain.repository.ProductsRepository
import br.com.digio.androidtest.domain.utils.DataError
import br.com.digio.androidtest.domain.utils.Result
import br.com.digio.androidtest.domain.utils.onSuccess
import kotlinx.coroutines.flow.firstOrNull

class FetchDigioProductsUseCase(
    private val productsRepository: ProductsRepository
) {

    suspend operator fun invoke(): Result<DigioProducts, DataError> {
        return try {
            val cachedProducts = productsRepository.getDigioProducts().firstOrNull()
            if (cachedProducts != null) {
                Result.Success(cachedProducts)
            } else {
                val apiResult = productsRepository.fetchDigioProducts()
                apiResult.onSuccess { products ->
                    productsRepository.saveDigioProducts(products)
                }
                apiResult
            }
        } catch (exception: Exception) {
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }
}

