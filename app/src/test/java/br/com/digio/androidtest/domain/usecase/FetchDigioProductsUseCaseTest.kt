package br.com.digio.androidtest.domain.usecase

import br.com.digio.androidtest.domain.repository.ProductsRepository
import br.com.digio.androidtest.domain.utils.DataError
import br.com.digio.androidtest.domain.utils.Result
import br.com.digio.androidtest.utils.TesteUtils
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FetchDigioProductsUseCaseTest {

    private val productsRepository: ProductsRepository = mockk(relaxed = true)

    private lateinit var fetchDigioProductsUseCase: FetchDigioProductsUseCase

    @Before
    fun setUp() {
        fetchDigioProductsUseCase = FetchDigioProductsUseCase(productsRepository)
    }

    @Test
    fun `When there are products in cache, it should return Result_Success with the cache content and not call the remote fetch`() = runTest {

        coEvery { productsRepository.getDigioProducts() } returns flowOf(TesteUtils.mockDigioProducts)

        val result = fetchDigioProductsUseCase()

        assertTrue(result is Result.Success)
        assertEquals(TesteUtils.mockDigioProducts, (result as Result.Success).data)

        coVerify(exactly = 0) { productsRepository.fetchDigioProducts() }
        coVerify(exactly = 0) { productsRepository.saveDigioProducts(any()) }
    }

    @Test
    fun `When there are no products in cache and the remote fetch returns success, it should save to the cache and return Success`() = runTest {

        coEvery { productsRepository.getDigioProducts() } returns flowOf(null)
        coEvery { productsRepository.fetchDigioProducts() } returns Result.Success(TesteUtils.mockDigioProducts)

        val result = fetchDigioProductsUseCase()

        assertTrue(result is Result.Success)
        assertEquals(TesteUtils.mockDigioProducts, (result as Result.Success).data)

        coVerify(exactly = 1) { productsRepository.fetchDigioProducts() }
        coVerify(exactly = 1) { productsRepository.saveDigioProducts(TesteUtils.mockDigioProducts) }
    }

    @Test
    fun `When there are no products in cache and the remote fetch returns an error, it should return the same error and not save`() = runTest {

        coEvery { productsRepository.getDigioProducts() } returns flowOf(null)
        coEvery { productsRepository.fetchDigioProducts() } returns Result.Error(DataError.Remote.NO_INTERNET)

        val result = fetchDigioProductsUseCase()

        assertTrue(result is Result.Error)
        assertEquals(DataError.Remote.NO_INTERNET, (result as Result.Error).error)

        coVerify(exactly = 1) { productsRepository.fetchDigioProducts() }
        coVerify(exactly = 0) { productsRepository.saveDigioProducts(any()) }
    }

    @Test
    fun `When there are no products in cache and the remote fetch throws an exception, it should return DataError_Remote_UNKNOWN`() = runTest {

        coEvery { productsRepository.getDigioProducts() } returns flowOf(null)
        coEvery { productsRepository.fetchDigioProducts() } throws RuntimeException("Erro de rede simulado")

        val result = fetchDigioProductsUseCase()

        assertTrue(result is Result.Error)
        assertEquals(DataError.Remote.UNKNOWN, (result as Result.Error).error)

        coVerify(exactly = 1) { productsRepository.fetchDigioProducts() }
        coVerify(exactly = 0) { productsRepository.saveDigioProducts(any()) }
    }
}
