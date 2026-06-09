package com.example.ecommerce.data.repository

import com.example.ecommerce.data.dto.*
import com.example.ecommerce.data.remote.ApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.eq

class ProductRepositoryImplTest {

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var repository: ProductRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = ProductRepositoryImpl(apiService)
    }

    @Test
    fun `getProductDetails returns mapped Product when API call is successful`() = runTest {
        // Arrange
        val productId = "123"
        val sku = "SKU123"
        val mockResponse = ProductDetailsResponse(
            status = 200,
            message = "Success",
            data = ProductData(
                id = "123",
                sku = "SKU123",
                name = "Test Product",
                price = "100.0",
                finalPrice = "80.0",
                brandName = "Test Brand",
                brandBannerUrl = "url",
                isSalable = true,
                image = "main_image",
                description = "Description",
                remainingQty = 10,
                images = listOf("image1", "image2"),
                configurableOptions = listOf(
                    ConfigurableOptionDto(
                        attributeId = 1,
                        type = "color",
                        attributeCode = "color_code",
                        attributes = listOf(
                            AttributeValueDto(
                                value = "Red",
                                optionId = "red_id",
                                price = "80.0",
                                images = emptyList(),
                                swatchUrl = "swatch_url",
                                colorCode = "#FF0000"
                            )
                        )
                    )
                ),
                review = ReviewDto(totalReview = 5, ratingCount = 4.5)
            )
        )

        `when`(apiService.getProductDetails(eq(productId), eq(sku), any(), any()))
            .thenReturn(mockResponse)

        // Act
        val result = repository.getProductDetails(productId, sku)

        // Assert
        assertEquals("123", result.id)
        assertEquals("Test Product", result.name)
        assertEquals(100.0, result.originalPrice, 0.0)
        assertEquals(80.0, result.finalPrice, 0.0)
        assertEquals(20, result.discountPercentage)
        assertEquals(1, result.configurableOptions.size)
        assertEquals("Red", result.configurableOptions[0].values[0].label)
        assertEquals(4.5, result.rating, 0.0)
    }

    @Test
    fun `getProductDetails throws Exception when API call status is not 200`() = runTest {
        // Arrange
        val productId = "123"
        val sku = "SKU123"
        val mockResponse = ProductDetailsResponse(
            status = 404,
            message = "Product not found",
            data = null
        )

        `when`(apiService.getProductDetails(eq(productId), eq(sku), any(), any()))
            .thenReturn(mockResponse)

        // Act & Assert
        try {
            repository.getProductDetails(productId, sku)
            fail("Should have thrown an Exception")
        } catch (e: Exception) {
            assertEquals("Product not found", e.message)
        }
    }

    @Test
    fun `getProductDetails throws Exception when data is null`() = runTest {
        // Arrange
        val productId = "123"
        val sku = "SKU123"
        val mockResponse = ProductDetailsResponse(
            status = 200,
            message = null, // Changed from "Success" to null so it uses the default error message
            data = null
        )

        `when`(apiService.getProductDetails(eq(productId), eq(sku), any(), any()))
            .thenReturn(mockResponse)

        // Act & Assert
        try {
            repository.getProductDetails(productId, sku)
            fail("Should have thrown an Exception")
        } catch (e: Exception) {
            assertEquals("Failed to load product details", e.message)
        }
    }
}
