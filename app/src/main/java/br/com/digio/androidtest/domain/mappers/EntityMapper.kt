package br.com.digio.androidtest.domain.mappers

interface EntityMapper<in Model, out T> {
    fun toEntity(from: Model): T
    fun toEntity(from: List<Model>): List<T> = from.map { toEntity(it) }
}

interface FromEntityMapper<out Model, in T> {
    fun fromEntity(from: T): Model
    fun fromEntity(from: List<T>): List<Model> = from.map { fromEntity(it) }
}