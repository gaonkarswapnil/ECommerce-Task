package com.example.ecommerce.ui.view.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.ecommerce.domain.model.OptionValue
import com.example.ecommerce.domain.model.Product
import com.example.ecommerce.domain.model.ProductOption
import com.example.ecommerce.ui.state.ProductUiState
import com.example.ecommerce.ui.theme.ECommerceTheme
import com.example.ecommerce.ui.view.components.BottomActionBar
import com.example.ecommerce.ui.view.components.ErrorScreen
import com.example.ecommerce.ui.view.components.ProductDetailsContent
import com.example.ecommerce.ui.view.components.ShimmerProductDetails
import com.example.ecommerce.ui.viewmodel.ProductViewModel

@Composable
fun ProductDetailsScreen(
    viewModel: ProductViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val isWishlisted by viewModel.isWishlisted.collectAsState()
    val selectedColor by viewModel.selectedColor.collectAsState()
    val quantity by viewModel.quantity.collectAsState()

    ProductDetailsScreenContent(
        uiState = uiState,
        isWishlisted = isWishlisted,
        selectedColor = selectedColor,
        quantity = quantity,
        onRefresh = { viewModel.loadProductDetails("6701", "253620") },
        onWishlistToggle = { viewModel.toggleWishlist() },
        onColorSelected = { viewModel.selectColor(it) },
        onIncrementQty = { viewModel.incrementQuantity() },
        onDecrementQty = { viewModel.decrementQuantity() },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreenContent(
    uiState: ProductUiState,
    isWishlisted: Boolean,
    selectedColor: OptionValue?,
    quantity: Int,
    onRefresh: () -> Unit,
    onWishlistToggle: () -> Unit,
    onColorSelected: (OptionValue) -> Unit,
    onIncrementQty: () -> Unit,
    onDecrementQty: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isRefreshing by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Product Details",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
                    }
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        bottomBar = {
            if (uiState is ProductUiState.Success) {
                BottomActionBar(
                    product = uiState.product,
                    selectedColor = selectedColor,
                    isWishlisted = isWishlisted,
                    onWishlistToggle = onWishlistToggle,
                    onAddToCart = { }
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                onRefresh()
                isRefreshing = false
            },
            modifier = Modifier.padding(innerPadding)
        ) {
            when (uiState) {
                is ProductUiState.Loading -> {
                    ShimmerProductDetails()
                }
                is ProductUiState.Success -> {
                    ProductDetailsContent(
                        product = uiState.product,
                        selectedColor = selectedColor,
                        quantity = quantity,
                        onColorSelected = onColorSelected,
                        onIncrementQty = onIncrementQty,
                        onDecrementQty = onDecrementQty
                    )
                }
                is ProductUiState.Error -> {
                    ErrorScreen(
                        message = uiState.message,
                        onRetry = onRefresh
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailsScreenSuccessPreview() {
    val sampleColors = listOf(
        OptionValue(id = "1", label = "Sky Blue", price = 85.0, images = emptyList(), swatchUrl = null, colorCode = "#87CEEB"),
        OptionValue(id = "2", label = "Hazel", price = 80.0, images = emptyList(), swatchUrl = null, colorCode = "#8E7618")
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
        description = "<h1>Overview</h1><p>Exceptional comfort for your eyes.</p>",
        rating = 4.8,
        reviewCount = 124,
        configurableOptions = listOf(
            ProductOption(attributeId = 1, type = "color", code = "color", values = sampleColors)
        )
    )

    ECommerceTheme {
        ProductDetailsScreenContent(
            uiState = ProductUiState.Success(sampleProduct),
            isWishlisted = true,
            selectedColor = sampleColors[0],
            quantity = 1,
            onRefresh = {},
            onWishlistToggle = {},
            onColorSelected = {},
            onIncrementQty = {},
            onDecrementQty = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailsScreenLoadingPreview() {
    ECommerceTheme {
        ProductDetailsScreenContent(
            uiState = ProductUiState.Loading,
            isWishlisted = false,
            selectedColor = null,
            quantity = 1,
            onRefresh = {},
            onWishlistToggle = {},
            onColorSelected = {},
            onIncrementQty = {},
            onDecrementQty = {}
        )
    }
}
