package com.example.ecommerce.data.dto

import com.google.gson.annotations.SerializedName

data class ProductDetailsResponse(
    @SerializedName("status") val status: Int?,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: ProductData?
)
