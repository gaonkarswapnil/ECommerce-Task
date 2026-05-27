package com.example.ecommerce.domain.model

data class ProductOption(
    val attributeId: Int,
    val type: String,
    val code: String,
    val values: List<OptionValue>
)
