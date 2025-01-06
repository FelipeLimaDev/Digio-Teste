package br.com.digio.androidtest.presentation.models

import br.com.digio.androidtest.domain.model.DigioProducts
import br.com.digio.androidtest.domain.utils.DataError

sealed interface ProductViewState {
    data object Loading: ProductViewState
    data class Success(val digioProducts: DigioProducts): ProductViewState
    data class Error(val dataError: DataError): ProductViewState
}