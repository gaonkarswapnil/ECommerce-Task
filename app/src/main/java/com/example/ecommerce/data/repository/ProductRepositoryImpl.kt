package com.example.ecommerce.data.repository

import com.example.ecommerce.data.dto.ProductDetailsResponse
import com.example.ecommerce.data.remote.ApiService
import com.example.ecommerce.domain.model.OptionValue
import com.example.ecommerce.domain.model.Product
import com.example.ecommerce.domain.model.ProductOption
import com.example.ecommerce.domain.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {

    override suspend fun getProductDetails(productId: String, sku: String): Product {
        val response = apiService.getProductDetails(productId, sku)
        if (response.status == 200 && response.data != null) {
            val data = response.data
            val originalPrice = data.price?.toDoubleOrNull() ?: 0.0
            val finalPrice = data.finalPrice?.toDoubleOrNull() ?: 0.0
            
            val discountPercentage = if (originalPrice > 0.0 && finalPrice < originalPrice) {
                (((originalPrice - finalPrice) / originalPrice) * 100).toInt()
            } else {
                0
            }

            val mappedOptions = data.configurableOptions?.map { optionDto ->
                ProductOption(
                    attributeId = optionDto.attributeId ?: 0,
                    type = optionDto.type ?: "",
                    code = optionDto.attributeCode ?: "",
                    values = optionDto.attributes?.map { valueDto ->
                        OptionValue(
                            id = valueDto.optionId ?: "",
                            label = valueDto.value ?: "",
                            price = valueDto.price?.toDoubleOrNull() ?: finalPrice,
                            images = valueDto.images ?: emptyList(),
                            swatchUrl = valueDto.swatchUrl,
                            colorCode = valueDto.colorCode
                        )
                    } ?: emptyList()
                )
            } ?: emptyList()

            return Product(
                id = data.id ?: "",
                sku = data.sku ?: "",
                name = data.name ?: "",
                brandName = data.brandName ?: "",
                brandBannerUrl = data.brandBannerUrl,
                originalPrice = originalPrice,
                finalPrice = finalPrice,
                discountPercentage = discountPercentage,
                isSalable = data.isSalable ?: false,
                mainImage = data.image ?: "",
                remainingQty = data.remainingQty ?: 0,
                images = data.images ?: emptyList(),
                description = data.description ?: "",
                rating = data.review?.ratingCount ?: 0.0,
                reviewCount = data.review?.totalReview ?: 0,
                configurableOptions = mappedOptions
            )
        } else {
            throw Exception(response.message ?: "Failed to load product details")
        }
    }
}
