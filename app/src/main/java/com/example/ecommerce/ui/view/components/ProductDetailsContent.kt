package com.example.ecommerce.ui.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.ecommerce.domain.model.OptionValue
import com.example.ecommerce.domain.model.Product
import com.example.ecommerce.domain.model.ProductOption
import com.example.ecommerce.ui.theme.ECommerceTheme

@Composable
fun ProductDetailsContent(
    product: Product,
    selectedColor: OptionValue?,
    quantity: Int,
    onColorSelected: (OptionValue) -> Unit,
    onIncrementQty: () -> Unit,
    onDecrementQty: () -> Unit,
    modifier: Modifier = Modifier
) {
    val carouselImages = if (!selectedColor?.images.isNullOrEmpty()) {
        selectedColor!!.images
    } else {
        product.images.ifEmpty { listOf(product.mainImage) }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            ImageCarousel(images = carouselImages)
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.brandName.uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 1.2.sp
                    )

                    if (!product.brandBannerUrl.isNullOrEmpty()) {
                        SubcomposeAsyncImage(
                            model = product.brandBannerUrl,
                            contentDescription = "Brand Logo",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .height(24.dp)
                                .width(70.dp)
                                .clip(RoundedCornerShape(4.dp))
                        )
                    }
                }

                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RatingBar(rating = product.rating)
                    Text(
                        text = "${product.rating}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "(${product.reviewCount} reviews)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                )
            }
        }

        item {
            val displayPrice = selectedColor?.price ?: product.finalPrice
            val discountVal = product.discountPercentage

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "KWD ${String.format("%.3f", displayPrice)}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                if (discountVal > 0) {
                    Text(
                        text = "KWD ${String.format("%.3f", product.originalPrice)}",
                        style = MaterialTheme.typography.titleMedium,
                        textDecoration = TextDecoration.LineThrough,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                    )

                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFE8F5E9),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "$discountVal% OFF",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF2E7D32),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        item {
            val colorOption = product.configurableOptions.firstOrNull {
                it.type.equals("color", ignoreCase = true) || it.code.equals("color", ignoreCase = true)
            }

            if (colorOption != null && colorOption.values.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "COLOR: ${selectedColor?.label ?: ""}",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${colorOption.values.size} Colors available",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                        )
                    }

                    VariantSelector(
                        options = colorOption.values,
                        selectedOption = selectedColor,
                        onOptionSelected = onColorSelected
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StockChip(isSalable = product.isSalable, remainingQty = product.remainingQty)

                if (product.isSalable) {
                    QuantitySelector(
                        quantity = quantity,
                        onIncrement = onIncrementQty,
                        onDecrement = onDecrementQty
                    )
                }
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "SPECIFICATIONS",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SpecRow(label = "Base Curve", value = "8.6 mm")
                        SpecRow(label = "Diameter", value = "14.2 mm")
                        SpecRow(label = "Duration", value = "Weekly Disposable")
                        SpecRow(label = "Certification", value = "FDA Approved")
                        SpecRow(label = "Material", value = "HEMA (Hydrogel)")
                    }
                }
            }
        }

        item {
            DescriptionSection(descriptionHtml = product.description)
        }
    }
}

@Preview(showBackground = true, name = "Product Available")
@Composable
fun ProductDetailsContentPreview() {
    val sampleColors = listOf(
        OptionValue(id = "1", label = "Sky Blue", price = 85.0, images = emptyList(), swatchUrl = null, colorCode = "#87CEEB"),
        OptionValue(id = "2", label = "Hazel", price = 80.0, images = emptyList(), swatchUrl = null, colorCode = "#8E7618"),
        OptionValue(id = "3", label = "Emerald", price = 90.0, images = emptyList(), swatchUrl = null, colorCode = "#50C878")
    )

    val sampleProduct = Product(
        id = "1",
        sku = "LENS-001",
        name = "Acuvue Oasys Weekly Contact Lenses",
        brandName = "Acuvue",
        brandBannerUrl = null,
        originalPrice = 100.0,
        finalPrice = 80.0,
        discountPercentage = 20,
        isSalable = true,
        mainImage = "",
        remainingQty = 15,
        images = emptyList(),
        description = "<h1>Product Overview</h1><p>Acuvue Oasys for ASTIGMATISM are designed to provide <b>exceptional comfort</b> throughout your day.</p><ul><li>Hydraclear Plus Technology</li><li>UV Protection</li><li>Easy to handle</li></ul>",
        rating = 4.8,
        reviewCount = 124,
        configurableOptions = listOf(
            ProductOption(attributeId = 1, type = "color", code = "color", values = sampleColors)
        )
    )

    ECommerceTheme {
        ProductDetailsContent(
            product = sampleProduct,
            selectedColor = sampleColors[0],
            quantity = 1,
            onColorSelected = {},
            onIncrementQty = {},
            onDecrementQty = {}
        )
    }
}

@Preview(showBackground = true, name = "Out of Stock")
@Composable
fun ProductOutOfStockPreview() {
    val sampleProduct = Product(
        id = "2",
        sku = "LENS-002",
        name = "Biotrue Oneday (Out of Stock)",
        brandName = "Bausch + Lomb",
        brandBannerUrl = null,
        originalPrice = 50.0,
        finalPrice = 50.0,
        discountPercentage = 0,
        isSalable = false,
        mainImage = "",
        remainingQty = 0,
        images = emptyList(),
        description = "<p>Product is currently unavailable.</p>",
        rating = 4.2,
        reviewCount = 45,
        configurableOptions = emptyList()
    )

    ECommerceTheme {
        ProductDetailsContent(
            product = sampleProduct,
            selectedColor = null,
            quantity = 1,
            onColorSelected = {},
            onIncrementQty = {},
            onDecrementQty = {}
        )
    }
}
