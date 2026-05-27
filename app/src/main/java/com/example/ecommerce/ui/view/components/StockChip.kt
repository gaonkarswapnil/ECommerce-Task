package com.example.ecommerce.ui.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecommerce.ui.theme.ECommerceTheme

@Composable
fun StockChip(
    isSalable: Boolean,
    remainingQty: Int,
    modifier: Modifier = Modifier
) {
    val containerColor = if (isSalable) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
    val contentColor = if (isSalable) Color(0xFF2E7D32) else Color(0xFFC62828)
    val text = if (isSalable) {
        if (remainingQty in 1..20) "Only $remainingQty left in stock!" else "In Stock"
    } else {
        "Out of Stock"
    }
    val icon = if (isSalable) Icons.Default.CheckCircle else Icons.Default.Warning

    Row(
        modifier = modifier
            .background(containerColor, RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = contentColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StockChipPreview() {
    ECommerceTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StockChip(isSalable = true, remainingQty = 50)
            StockChip(isSalable = true, remainingQty = 5)
            StockChip(isSalable = false, remainingQty = 0)
        }
    }
}
