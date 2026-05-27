package com.example.ecommerce.data.remote

import com.example.ecommerce.data.dto.ProductDetailsResponse
import com.example.ecommerce.util.constant.StringConstants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(StringConstants.API_ENDPOINT)
    suspend fun getProductDetails(
        @Path("productId") productId: String,
        @Path("sku") sku: String,
        @Query("lang") lang: String = "en",
        @Query("store") store: String = "KWD"
    ): ProductDetailsResponse
}
