package com.lubnamariyam.lubsboutique

import android.app.Activity
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lubnamariyam.lubsboutique.model.Product
import com.lubnamariyam.lubsboutique.model.ProductResponse
import com.lubnamariyam.lubsboutique.ui.theme.LubsBoutiqueTheme
import com.lubnamariyam.lubsboutique.view.CartPage
import com.lubnamariyam.lubsboutique.view.HomeScreen
import com.lubnamariyam.lubsboutique.view.ProductDetailPage
import com.lubnamariyam.lubsboutique.viewModel.HomeViewModel
import com.lubnamariyam.lubsboutique.viewModel.ProductViewModel
import kotlinx.coroutines.delay

public class MainActivity : ComponentActivity() {
    val homeViewModel by viewModels<HomeViewModel>()
    companion object{
        var tempProduct: Product? = null
    }

    var productViewModel : ProductViewModel? = null

    

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        setContent {
            LubsBoutiqueTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Navigation(homeViewModel, productViewModel!!)
                    homeViewModel.getProductList()
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun Navigation(viewModel: HomeViewModel , productViewModel: ProductViewModel) {
    val navController = rememberNavController()

        NavHost(navController = navController,
            startDestination = "splash_screen") {
            composable("splash_screen") {
                SplashScreen(navController = navController)
            }
            // Products screen
            composable("main_screen") {
                val activity = (LocalContext.current as? Activity)
                ProductList(productList = viewModel.productListResponse, navController = navController, activity = activity!!)
            }
            // Product Details Screen
            composable("product_detail") {
                Column() {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Product Detail",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.SansSerif
                            )
                        } ,
                        navigationIcon = {
                            IconButton(onClick = { navController.navigate("main_screen") }) {
                                Icon(Icons.Filled.ArrowBack, null)
                            }
                        },
                        actions = {
                            IconButton(onClick = {/* Do Something*/ }) {
                                Icon(Icons.Filled.ShoppingCart, null)
                            }
                        }
                    )
                    ProductDetailPage(product = MainActivity.tempProduct!!, productViewModel, navController)
                }
            }

            // Cart Screen
            composable("cart_screen") {
                CartPage(productViewModel = productViewModel)
            }

        }
}



@ExperimentalFoundationApi
@Composable
fun ProductList(productList: ProductResponse , navController: NavController , activity: Activity) {
    println("Hello  " + productList)
    Column() {
        TopAppBar(
            title = {
                Text(
                    text = "Lubs Boutique",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
            } ,
            navigationIcon = {
                IconButton(onClick = {activity.finish() }) {
                    Icon(Icons.Filled.ArrowBack, null)
                }
            },
            actions = {
                IconButton(onClick = {navController.navigate("cart_screen") }) {
                    Icon(Icons.Filled.ShoppingCart, null)
                }
            }
        )
        LazyVerticalGrid(GridCells.Fixed(2)) {
            items(productList.products.size) { index ->
                HomeScreen(
                    navController = navController,
                    activity = activity,
                    productList = productList.products[index]
                )
            }
        }
    }

}



@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    // AnimationEffect

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )

        delay(2000L)
        navController.navigate("main_screen")
    }

    // Image -> Logo
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "Logo",
            modifier = Modifier
                .scale(scale.value)
                .size(300.dp, 300.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LubsBoutiqueTheme {

    }
}