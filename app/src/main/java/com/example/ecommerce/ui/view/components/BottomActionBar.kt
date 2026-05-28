package com.example.ecommerce.ui.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(8.dp, shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp)),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Primary CTA: Add to bag
            Button(
                onClick = onAddToCart,
                enabled = product.isSalable,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFF444444),
                    disabledContentColor = Color.White
                ),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = if (product.isSalable) "Add to bag" else "Out of Stock",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }

            // Secondary CTA: Share
            OutlinedButton(
                onClick = { },
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = "Share",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
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
