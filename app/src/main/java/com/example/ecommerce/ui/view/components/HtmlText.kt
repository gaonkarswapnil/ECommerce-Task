package com.example.ecommerce.ui.view.components

import android.widget.TextView
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.example.ecommerce.ui.theme.ECommerceTheme

@Composable
fun HtmlText(
    html: String,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    fontSize: Float = 14f
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            TextView(context).apply {
                this.textSize = fontSize
                this.setTextColor(android.graphics.Color.argb(
                    (textColor.alpha * 255).toInt(),
                    (textColor.red * 255).toInt(),
                    (textColor.green * 255).toInt(),
                    (textColor.blue * 255).toInt()
                ))
            }
        },
        update = { textView ->
            textView.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HtmlTextPreview() {
    ECommerceTheme {
        HtmlText(
            html = "<h1>Product Details</h1><p>This is a <b>bold</b> and <i>italic</i> description rendered from <u>HTML</u> content.</p>",
            fontSize = 16f
        )
    }
}
