package com.example.ecommerce.ui.view.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ecommerce.ui.theme.ECommerceTheme

@Composable
fun RatingBar(
    rating: Double,
    modifier: Modifier = Modifier,
    starSize: Dp = 18.dp,
    maxStars: Int = 5
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        repeat(maxStars) { index ->
            val activeColor = if (index < rating.toInt()) {
                Color(0xFFFFB300)
            } else {
                Color(0xFFE0E0E0)
            }
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star ${index + 1}",
                tint = activeColor,
                modifier = Modifier.size(starSize)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RatingBarPreview() {
    ECommerceTheme {
        RatingBar(rating = 4.0)
    }
}
