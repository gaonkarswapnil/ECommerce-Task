package com.example.ecommerce.data.dto

import com.google.gson.annotations.SerializedName

data class ConfigurableOptionDto(
    @SerializedName("attribute_id") val attributeId: Int?,
    @SerializedName("type") val type: String?,
    @SerializedName("attribute_code") val attributeCode: String?,
    @SerializedName("attributes") val attributes: List<AttributeValueDto>?
)
