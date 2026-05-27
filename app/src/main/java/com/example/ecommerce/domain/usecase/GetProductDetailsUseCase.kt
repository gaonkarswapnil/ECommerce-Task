package com.example.ecommerce.domain.usecase

import com.example.ecommerce.domain.model.Product
import com.example.ecommerce.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productId: String, sku: String): Product {
        return repository.getProductDetails(productId, sku)
    }
}
