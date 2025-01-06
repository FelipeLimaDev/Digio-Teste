package br.com.digio.androidtest.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.digio.androidtest.data.source.local.dao.DigioProductsDao
import br.com.digio.androidtest.data.source.local.entities.DigioProductsEntity

@TypeConverters(DigioTypeConverters::class)
@Database(
    entities = [DigioProductsEntity::class],
    version = 1
)
abstract class DigioDatabase : RoomDatabase() {
    abstract val digioProductsDao: DigioProductsDao

    companion object {
        const val DATABASE_NAME = "digio_database"
    }
}