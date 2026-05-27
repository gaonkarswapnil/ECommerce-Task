package com.example.ecommerce.data.dto

import com.google.gson.annotations.SerializedName

data class ProductData(
    @SerializedName("id") val id: String?,
    @SerializedName("sku") val sku: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("price") val price: String?,
    @SerializedName("final_price") val finalPrice: String?,
    @SerializedName("brand_name") val brandName: String?,
    @SerializedName("brand_banner_url") val brandBannerUrl: String?,
    @SerializedName("is_salable") val isSalable: Boolean?,
    @SerializedName("image") val image: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("remaining_qty") val remainingQty: Int?,
    @SerializedName("images") val images: List<String>?,
    @SerializedName("configurable_option") val configurableOptions: List<ConfigurableOptionDto>?,
    @SerializedName("review") val review: ReviewDto?
)
