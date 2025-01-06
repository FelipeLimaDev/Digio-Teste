package br.com.digio.androidtest.domain.utils

sealed interface DataError: Error {
    enum class Remote: DataError {
        REQUEST_TIMEOUT,
        NO_INTERNET,
        UNKNOWN
    }

    enum class Local: DataError {
        UNKNOWN
    }
}