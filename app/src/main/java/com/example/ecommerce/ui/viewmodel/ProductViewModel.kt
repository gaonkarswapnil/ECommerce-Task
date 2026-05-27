package com.example.ecommerce.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.domain.model.OptionValue
import com.example.ecommerce.domain.model.Product
import com.example.ecommerce.domain.usecase.GetProductDetailsUseCase
import com.example.ecommerce.ui.state.ProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()


    private val _selectedColor = MutableStateFlow<OptionValue?>(null)
    val selectedColor: StateFlow<OptionValue?> = _selectedColor.asStateFlow()


    private val _isWishlisted = MutableStateFlow(false)
    val isWishlisted: StateFlow<Boolean> = _isWishlisted.asStateFlow()


    private val _quantity = MutableStateFlow(1)
    val quantity: StateFlow<Int> = _quantity.asStateFlow()


    private var currentProductId: String = "6701"
    private var currentSku: String = "253620"


    init {
        loadProductDetails(currentProductId, currentSku)
    }

    fun loadProductDetails(productId: String, sku: String) {
        currentProductId = productId
        currentSku = sku
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            try {
                val product = getProductDetailsUseCase(productId, sku)
                _uiState.value = ProductUiState.Success(product)
                val firstColorOption = product.configurableOptions
                    .firstOrNull { it.type == "color" || it.code == "color" }
                    ?.values?.firstOrNull()
                _selectedColor.value = firstColorOption
            } catch (e: Exception) {
                _uiState.value = ProductUiState.Error(e.localizedMessage ?: "An unexpected error occurred")
            }
        }
    }

    fun selectColor(colorOption: OptionValue) {
        _selectedColor.value = colorOption
    }

    fun toggleWishlist() {
        _isWishlisted.value = !_isWishlisted.value
    }

    fun incrementQuantity() {
        _quantity.value = _quantity.value + 1
    }

    fun decrementQuantity() {
        if (_quantity.value > 1) {
            _quantity.value = _quantity.value - 1
        }
    }
}
