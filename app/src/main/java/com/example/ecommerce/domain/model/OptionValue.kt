package com.example.ecommerce.domain.model

data class OptionValue(
    val id: String,
    val label: String,
    val price: Double,
    val images: List<String>,
    val swatchUrl: String?,
    val colorCode: String?
)
