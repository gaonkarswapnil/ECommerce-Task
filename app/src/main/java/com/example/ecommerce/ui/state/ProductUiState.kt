package com.example.ecommerce.ui.state

import com.example.ecommerce.domain.model.Product

sealed interface ProductUiState {
    object Loading : ProductUiState
    data class Success(val product: Product) : ProductUiState
    data class Error(val message: String) : ProductUiState
}
