package br.com.digio.androidtest.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import br.com.digio.androidtest.data.source.local.entities.DigioProductsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DigioProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDigioProducts(digioProducts: DigioProductsEntity)

    @Query("SELECT * FROM digioproducts LIMIT 1")
    fun getDigioProducts(): Flow<DigioProductsEntity?>

    @Delete
    suspend fun deleteDigioProducts(digioProducts: DigioProductsEntity)
}