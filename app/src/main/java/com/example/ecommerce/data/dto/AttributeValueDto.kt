package com.example.ecommerce.data.dto

import com.google.gson.annotations.SerializedName

data class AttributeValueDto(
    @SerializedName("value") val value: String?,
    @SerializedName("option_id") val optionId: String?,
    @SerializedName("price") val price: String?,
    @SerializedName("images") val images: List<String>?,
    @SerializedName("swatch_url") val swatchUrl: String?,
    @SerializedName("color_code") val colorCode: String?
)