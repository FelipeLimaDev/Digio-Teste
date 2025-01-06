
package br.com.digio.androidtest.presentation.viewmodels

import app.cash.turbine.test
import br.com.digio.androidtest.domain.usecase.FetchDigioProductsUseCase
import br.com.digio.androidtest.domain.utils.Result
import br.com.digio.androidtest.presentation.models.ProductViewState
import br.com.digio.androidtest.utils.CoroutinesTestRule
import br.com.digio.androidtest.utils.TesteUtils
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductsViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val fetchDigioProductsUseCase: FetchDigioProductsUseCase = mockk(relaxUnitFun = true)

    private lateinit var viewModel: ProductsViewModel

    @Before
    fun setUp() {
        viewModel = ProductsViewModel(fetchDigioProductsUseCase)
    }

    @Test
    fun `When FetchDigioProductsUseCase returns success, the state should be ProductViewState_Succes`() = runTest {

        coEvery { fetchDigioProductsUseCase() } returns Result.Success(TesteUtils.mockDigioProducts)

        viewModel.products.test {

            val successState = awaitItem() as ProductViewState.Success
            assertEquals(TesteUtils.mockDigioProducts, successState.digioProducts)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `When FetchDigioProductsUseCase returns a remote error, the state should be ProductViewState_Error`() = runTest {

        coEvery { fetchDigioProductsUseCase() } returns Result.Error(TesteUtils.mockDataErrorRemoteNoInternet)

        viewModel.products.test {

            val errorState = awaitItem() as ProductViewState.Error
            assertEquals(TesteUtils.mockDataErrorRemoteNoInternet, errorState.dataError)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `When FetchDigioProductsUseCase returns a local error, the state should be ProductViewState_Error`() = runTest {

        coEvery { fetchDigioProductsUseCase() } returns Result.Error(TesteUtils.mockDataErrorLocalUnknown)

        viewModel.products.test {

            val errorState = awaitItem() as ProductViewState.Error
            assertEquals(TesteUtils.mockDataErrorLocalUnknown, errorState.dataError)

            cancelAndConsumeRemainingEvents()
        }
    }
}
