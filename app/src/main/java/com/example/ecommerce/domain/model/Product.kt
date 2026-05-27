package com.example.ecommerce.domain.model

data class Product(
    val id: String,
    val sku: String,
    val name: String,
    val brandName: String,
    val brandBannerUrl: String?,
    val originalPrice: Double,
    val finalPrice: Double,
    val discountPercentage: Int,
    val isSalable: Boolean,
    val mainImage: String,
    val remainingQty: Int,
    val images: List<String>,
    val description: String,
    val rating: Double,
    val reviewCount: Int,
    val configurableOptions: List<ProductOption>
)
