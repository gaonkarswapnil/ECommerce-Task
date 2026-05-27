package com.example.ecommerce.ui.view.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.ecommerce.domain.model.OptionValue
import com.example.ecommerce.ui.theme.ECommerceTheme
import com.example.ecommerce.util.extension.shimmer

@Composable
fun VariantSelector(
    options: List<OptionValue>,
    selectedOption: OptionValue?,
    onOptionSelected: (OptionValue) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        options.forEach { option ->
            val isSelected = option.id == selectedOption?.id
            val borderColor by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.5f),
                label = "border_color"
            )
            val borderStrokeWidth = if (isSelected) 2.dp else 1.dp

            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .border(borderStrokeWidth, borderColor, CircleShape)
                    .clickable { onOptionSelected(option) }
                    .padding(2.dp)
            ) {
                if (!option.swatchUrl.isNullOrEmpty()) {
                    SubcomposeAsyncImage(
                        model = option.swatchUrl,
                        contentDescription = option.label,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        loading = {
                            Box(modifier = Modifier.fillMaxSize().shimmer())
                        }
                    )
                } else if (!option.colorCode.isNullOrEmpty()) {
                    val colorHex = option.colorCode.removePrefix("#")
                    val parsedColor = try {
                        Color(android.graphics.Color.parseColor("#$colorHex"))
                    } catch (e: Exception) {
                        MaterialTheme.colorScheme.surface
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(parsedColor)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = option.label.take(1),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VariantSelectorPreview() {
    val sampleOptions = listOf(
        OptionValue(id = "1", label = "Sky Blue", price = 85.0, images = emptyList(), swatchUrl = null, colorCode = "#87CEEB"),
        OptionValue(id = "2", label = "Hazel", price = 80.0, images = emptyList(), swatchUrl = null, colorCode = "#8E7618"),
        OptionValue(id = "3", label = "Emerald", price = 90.0, images = emptyList(), swatchUrl = null, colorCode = "#50C878"),
        OptionValue(id = "4", label = "Gray", price = 85.0, images = emptyList(), swatchUrl = null, colorCode = "#808080")
    )

    ECommerceTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            VariantSelector(
                options = sampleOptions,
                selectedOption = sampleOptions[0],
                onOptionSelected = {}
            )
        }
    }
}
