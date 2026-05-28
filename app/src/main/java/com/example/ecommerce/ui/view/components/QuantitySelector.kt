package com.example.ecommerce.ui.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import com.example.ecommerce.ui.theme.ECommerceTheme

@Composable
fun QuantitySelector(
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonSize = 40.dp
    val middleWidth = 48.dp

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // Decrement button — light gray
        Box(
            modifier = Modifier
                .size(buttonSize)
                .background(Color(0xFFE0E0E0), RoundedCornerShape(4.dp))
                .clickable { onDecrement() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "−",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF555555)
            )
        }

        // Quantity display — bordered box
        Box(
            modifier = Modifier
                .width(middleWidth)
                .height(buttonSize)
                .background(Color.White)
                .then(
                    Modifier.padding(horizontal = 1.dp) // thin side borders via background layering
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$quantity",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        // Increment button — black
        Box(
            modifier = Modifier
                .size(buttonSize)
                .background(Color.Black, RoundedCornerShape(4.dp))
                .clickable { onIncrement() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuantitySelectorPreview() {
    ECommerceTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            QuantitySelector(
                quantity = 1,
                onIncrement = {},
                onDecrement = {}
            )
        }
    }
}
