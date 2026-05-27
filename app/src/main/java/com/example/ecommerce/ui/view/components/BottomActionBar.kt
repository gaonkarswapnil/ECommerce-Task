package com.example.ecommerce.ui.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerce.domain.model.OptionValue
import com.example.ecommerce.domain.model.Product
import com.example.ecommerce.ui.theme.ECommerceTheme

@Composable
fun BottomActionBar(
    product: Product,
    selectedColor: OptionValue?,
    isWishlisted: Boolean,
    onWishlistToggle: () -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    val displayPrice = selectedColor?.price ?: product.finalPrice

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(16.dp, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column {
                Text(
                    text = "Total Price",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
                Text(
                    text = "KWD ${String.format("%.3f", displayPrice)}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            IconButton(
                onClick = onWishlistToggle,
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = if (isWishlisted) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Wishlist",
                    tint = if (isWishlisted) Color(0xFFE91E63) else MaterialTheme.colorScheme.onSurface
                )
            }

            Button(
                onClick = onAddToCart,
                enabled = product.isSalable,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = Color.LightGray
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (product.isSalable) "ADD TO CART" else "OUT OF STOCK",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomActionBarPreview() {
    val sampleProduct = Product(
        id = "1",
        sku = "SKU123",
        name = "Sample Product",
        brandName = "Sample Brand",
        brandBannerUrl = null,
        originalPrice = 100.0,
        finalPrice = 80.0,
        discountPercentage = 20,
        isSalable = true,
        mainImage = "",
        remainingQty = 10,
        images = emptyList(),
        description = "Description",
        rating = 4.5,
        reviewCount = 10,
        configurableOptions = emptyList()
    )

    ECommerceTheme {
        BottomActionBar(
            product = sampleProduct,
            selectedColor = null,
            isWishlisted = false,
            onWishlistToggle = {},
            onAddToCart = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomActionBarWishlistedPreview() {
    val sampleProduct = Product(
        id = "1",
        sku = "SKU123",
        name = "Sample Product",
        brandName = "Sample Brand",
        brandBannerUrl = null,
        originalPrice = 100.0,
        finalPrice = 80.0,
        discountPercentage = 20,
        isSalable = true,
        mainImage = "",
        remainingQty = 10,
        images = emptyList(),
        description = "Description",
        rating = 4.5,
        reviewCount = 10,
        configurableOptions = emptyList()
    )

    val sampleOption = OptionValue(
        id = "1",
        label = "Red",
        price = 85.0,
        images = emptyList(),
        swatchUrl = null,
        colorCode = "#FF0000"
    )

    ECommerceTheme {
        BottomActionBar(
            product = sampleProduct,
            selectedColor = sampleOption,
            isWishlisted = true,
            onWishlistToggle = {},
            onAddToCart = {}
        )
    }
}
