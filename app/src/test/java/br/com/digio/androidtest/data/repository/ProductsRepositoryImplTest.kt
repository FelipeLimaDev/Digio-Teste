package br.com.digio.androidtest.data.repository

import android.database.sqlite.SQLiteException
import br.com.digio.androidtest.data.source.local.dao.DigioProductsDao
import br.com.digio.androidtest.data.source.remote.RemoteDataSource
import br.com.digio.androidtest.domain.utils.DataError
import br.com.digio.androidtest.domain.utils.Result
import br.com.digio.androidtest.utils.TesteUtils
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ProductsRepositoryImplTest {

    private val mockRemoteDataSource = mockk<RemoteDataSource>()
    private val mockDao = mockk<DigioProductsDao>()

    private lateinit var repository: ProductsRepositoryImpl

    @Before
    fun setUp() {
        repository = ProductsRepositoryImpl(
            remoteDataSource = mockRemoteDataSource,
            dao = mockDao
        )
    }

    @Test
    fun `fetchDigioProducts should return Success when RemoteDataSource returns DigioProductsDto successfully`() = runTest {

        coEvery { mockRemoteDataSource.fetchDigioProducts() } returns TesteUtils.mockDigioProductsDto

        val result = repository.fetchDigioProducts()

        assertTrue(result is Result.Success)
        val successResult = result as Result.Success

        assertEquals(TesteUtils.mockDigioProducts, successResult.data)

        coVerify(exactly = 1) { mockRemoteDataSource.fetchDigioProducts() }
        confirmVerified(mockRemoteDataSource)
    }

    @Test
    fun `fetchDigioProducts should return Error(NO_INTERNET) if an UnknownHostException`() = runTest {

        coEvery { mockRemoteDataSource.fetchDigioProducts() } throws UnknownHostException()

        val result = repository.fetchDigioProducts()

        assertTrue(result is Result.Error)
        val errorResult = result as Result.Error
        assertEquals(DataError.Remote.NO_INTERNET, errorResult.error)

        coVerify(exactly = 1) { mockRemoteDataSource.fetchDigioProducts() }
        confirmVerified(mockRemoteDataSource)
    }

    @Test
    fun `fetchDigioProducts should return Error(REQUEST_TIMEOUT) if a SocketTimeoutException`() = runTest {

        coEvery { mockRemoteDataSource.fetchDigioProducts() } throws SocketTimeoutException()

        val result = repository.fetchDigioProducts()

        assertTrue(result is Result.Error)
        val errorResult = result as Result.Error
        assertEquals(DataError.Remote.REQUEST_TIMEOUT, errorResult.error)

        coVerify(exactly = 1) { mockRemoteDataSource.fetchDigioProducts() }
        confirmVerified(mockRemoteDataSource)
    }

    @Test
    fun `fetchDigioProducts should return Error(UNKNOWN) if a generic Exception is thrown`() = runTest {

        coEvery { mockRemoteDataSource.fetchDigioProducts() } throws RuntimeException("Algum erro")

        val result = repository.fetchDigioProducts()

        assertTrue(result is Result.Error)
        val errorResult = result as Result.Error
        assertEquals(DataError.Remote.UNKNOWN, errorResult.error)

        coVerify(exactly = 1) { mockRemoteDataSource.fetchDigioProducts() }
        confirmVerified(mockRemoteDataSource)
    }

    @Test
    fun `saveDigioProducts should return Success(Unit) when inserting into the DAO without error`() = runTest {

        coEvery { mockDao.insertDigioProducts(any()) } just Runs

        val result = repository.saveDigioProducts(TesteUtils.mockDigioProducts)

        assertTrue(result is Result.Success)
        coVerify(exactly = 1) {
            mockDao.insertDigioProducts(any())
        }
        confirmVerified(mockDao)
    }

    @Test
    fun `saveDigioProducts should return Error(UNKNOWN) if a SQLiteException is thrown`() = runTest {

        coEvery { mockDao.insertDigioProducts(any()) } throws SQLiteException("Erro no banco")

        val result = repository.saveDigioProducts(TesteUtils.mockDigioProducts)

        assertTrue(result is Result.Error)
        val errorResult = result as Result.Error
        assertEquals(DataError.Local.UNKNOWN, errorResult.error)

        coVerify(exactly = 1) { mockDao.insertDigioProducts(any()) }
        confirmVerified(mockDao)
    }

    @Test
    fun `getDigioProducts should return a flow with DigioProductsDomain`() = runTest {

        coEvery { mockDao.getDigioProducts() } returns flowOf(TesteUtils.mockDigioProductsEntity)

        val flowResult = repository.getDigioProducts()

        val firstValue = flowResult.firstOrNull()
        assertEquals(TesteUtils.mockDigioProducts, firstValue)

        coVerify(exactly = 1) { mockDao.getDigioProducts() }
        confirmVerified(mockDao)
    }

    @Test
    fun `getDigioProducts should return a flow with null when no entity is found`() = runTest {

        coEvery { mockDao.getDigioProducts() } returns flowOf(null)

        val flowResult = repository.getDigioProducts()

        val firstValue = flowResult.firstOrNull()
        assertNull(firstValue)

        coVerify(exactly = 1) { mockDao.getDigioProducts() }
        confirmVerified(mockDao)
    }
}
