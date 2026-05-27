package com.example.ecommerce.domain.repository

import com.example.ecommerce.domain.model.Product

interface ProductRepository {
    suspend fun getProductDetails(productId: String, sku: String): Product
}
