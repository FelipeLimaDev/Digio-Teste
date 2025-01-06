package br.com.digio.androidtest.data.repository

import android.database.sqlite.SQLiteException
import br.com.digio.androidtest.data.mappers.DigioProductsMapper
import br.com.digio.androidtest.data.source.local.dao.DigioProductsDao
import br.com.digio.androidtest.data.source.remote.RemoteDataSource
import br.com.digio.androidtest.domain.model.DigioProducts
import br.com.digio.androidtest.domain.repository.ProductsRepository
import br.com.digio.androidtest.domain.utils.DataError
import br.com.digio.androidtest.domain.utils.EmptyResult
import br.com.digio.androidtest.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class ProductsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val dao: DigioProductsDao,
) : ProductsRepository {
    override suspend fun fetchDigioProducts(): Result<DigioProducts, DataError> {
        return try {
            val response = remoteDataSource.fetchDigioProducts()
            val products = DigioProductsMapper().toDomain(response)
            Result.Success(products)
        } catch (e: UnknownHostException) {
            Result.Error(DataError.Remote.NO_INTERNET)
        } catch (e: SocketTimeoutException) {
            Result.Error(DataError.Remote.REQUEST_TIMEOUT)
        } catch (e: Exception) {
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }

    override suspend fun saveDigioProducts(digioProducts: DigioProducts): EmptyResult<DataError.Local> {
        return try {
            dao.insertDigioProducts(DigioProductsMapper().toEntity(digioProducts))
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override fun getDigioProducts(): Flow<DigioProducts?> {
        return dao.getDigioProducts()
            .map { entity -> entity?.let { DigioProductsMapper().fromEntity(it) } }
    }
}
