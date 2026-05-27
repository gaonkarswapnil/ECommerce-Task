package com.example.ecommerce.data.dto

import com.google.gson.annotations.SerializedName

data class ReviewDto(
    @SerializedName("total_review") val totalReview: Int?,
    @SerializedName("rating_count") val ratingCount: Double?
)
