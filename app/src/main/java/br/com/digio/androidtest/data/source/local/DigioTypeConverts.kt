package br.com.digio.androidtest.data.source.local

import androidx.room.TypeConverter
import br.com.digio.androidtest.domain.model.Cash
import br.com.digio.androidtest.domain.model.Product
import br.com.digio.androidtest.domain.model.Spotlight
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object DigioTypeConverters {

    private val gson = Gson()

    @TypeConverter
    fun fromProductList(value: List<Product>?): String {
        return gson.toJson(value ?:"")
    }

    @TypeConverter
    fun toProductList(value: String?): List<Product> {
        if (value.isNullOrEmpty()) return emptyList()

        val listType = object : TypeToken<List<Product>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }

    @TypeConverter
    fun fromSpotlightList(value: List<Spotlight>?): String {
        return gson.toJson(value ?: "")
    }

    @TypeConverter
    fun toSpotlightList(value: String?): List<Spotlight> {
        if (value.isNullOrEmpty()) return emptyList()

        val listType = object : TypeToken<List<Spotlight>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }

    @TypeConverter
    fun fromCash(value: Cash?): String {
        return gson.toJson(value ?: Cash("", ""))
    }

    @TypeConverter
    fun toCash(value: String?): Cash {
        if (value.isNullOrEmpty()) {
            return Cash("", "")
        }
        return gson.fromJson(value, Cash::class.java) ?: Cash("", "")
    }
}
