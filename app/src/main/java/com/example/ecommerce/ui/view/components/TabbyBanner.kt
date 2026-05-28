package com.example.ecommerce.ui.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerce.ui.theme.ECommerceTheme

@Composable
fun TabbyBanner(
    totalPrice: Double,
    modifier: Modifier = Modifier
) {
    val installment = totalPrice / 4.0
    val installmentText = String.format("%.2f", installment)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color(0xFFDDDDDD)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "or 4 interest-free payments",
                    fontSize = 12.sp,
                    color = Color(0xFF333333)
                )
                Text(
                    buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF111111))) {
                            append("$installmentText KWD ")
                        }
                        withStyle(SpanStyle(fontSize = 12.sp, color = Color(0xFF3D8BFF), fontStyle = FontStyle.Normal)) {
                            append("Learn More")
                        }
                    }
                )
            }

            // Tabby logo pill
            Box(
                modifier = Modifier
                    .background(Color(0xFF3FD07C), RoundedCornerShape(6.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "tabby",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = Color.Black,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TabbyBannerPreview() {
    ECommerceTheme {
        TabbyBanner(totalPrice = 3.50)
    }
}
