package br.com.digio.androidtest.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digio.androidtest.domain.usecase.FetchDigioProductsUseCase
import br.com.digio.androidtest.domain.utils.onError
import br.com.digio.androidtest.domain.utils.onSuccess
import br.com.digio.androidtest.presentation.models.ProductViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val fetchDigioProductsUseCase: FetchDigioProductsUseCase
) : ViewModel() {

    private val _products = MutableStateFlow<ProductViewState>(ProductViewState.Loading)
    val products = _products
        .onStart { loadProducts() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ProductViewState.Loading
        )


    private fun loadProducts() {
        viewModelScope.launch {
            val result = fetchDigioProductsUseCase()
            result.onSuccess { data ->
                _products.value = ProductViewState.Success(data)
            }.onError { error ->
                _products.value = ProductViewState.Error(error)
            }
        }
    }
}

