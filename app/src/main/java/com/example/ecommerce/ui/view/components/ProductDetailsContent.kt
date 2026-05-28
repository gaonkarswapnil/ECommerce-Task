package com.example.ecommerce.ui.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Row 1: product name (left) + price (right)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.name.uppercase(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.weight(1f)
                    )

                    val displayPrice = selectedColor?.price ?: product.finalPrice
                    Text(
                        text = "${String.format("%.2f", displayPrice)} KWD",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                // Row 2: collection / brand name
                Text(
                    text = product.brandName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )

                // Row 3: SKU
                Text(
                    text = "SKU: ${product.sku}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.45f)
                )
            }
        }

        // Price row is now shown inline in the name row above

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
                    Text(
                        text = "Color:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

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
