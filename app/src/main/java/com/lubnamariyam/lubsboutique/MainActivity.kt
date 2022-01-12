package com.lubnamariyam.lubsboutique

import android.app.Activity
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lubnamariyam.lubsboutique.Utility.ConnectionState
import com.lubnamariyam.lubsboutique.Utility.connectivityState
import com.lubnamariyam.lubsboutique.model.Product
import com.lubnamariyam.lubsboutique.model.ProductResponse
import com.lubnamariyam.lubsboutique.ui.theme.*
import com.lubnamariyam.lubsboutique.view.CartPage
import com.lubnamariyam.lubsboutique.view.HomeScreen
import com.lubnamariyam.lubsboutique.view.ProductDetailPage
import com.lubnamariyam.lubsboutique.viewModel.HomeViewModel
import com.lubnamariyam.lubsboutique.viewModel.ProductViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay

public class MainActivity : ComponentActivity() {
    val homeViewModel by viewModels<HomeViewModel>()

    companion object {
        var tempProduct: Product? = null
    }

    var productViewModel: ProductViewModel? = null

    @ExperimentalCoroutinesApi
    @ExperimentalAnimationApi
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

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun Navigation(viewModel: HomeViewModel, productViewModel: ProductViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        composable("splash_screen") {

            SplashScreen(navController = navController)
        }
        // Products screen
        composable("main_screen") {
            val activity = (LocalContext.current as? Activity)
            ProductList(
                productList = viewModel.productListResponse,
                navController = navController,
                activity = activity!!
            )
        }
        // Product Details Screen
        composable("product_detail") {
            Column() {
                TopAppBar(backgroundColor = com.lubnamariyam.lubsboutique.ui.theme.AppBar,
                    title = {
                        Text(
                            text = "Product Detail",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif, color = TextColor
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate("main_screen") }) {
                            Icon(Icons.Filled.ArrowBack, null, tint = TextColor)
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("cart_screen") }) {
                            Icon(Icons.Filled.ShoppingCart, null, tint = TextColor)
                        }
                    }
                )
                ConnectivityStatus()
                MainActivity.tempProduct?.let { it1 ->
                    ProductDetailPage(
                        product = it1,
                        productViewModel,
                        navController
                    )
                }
            }
        }

        // Cart Screen
        composable("cart_screen") {
            val data = productViewModel.getAllProduct().observeAsState(arrayListOf())
            var cartTotal = ""
            var savings = ""
            var finalPrice = ""
            val it = data.value
            var mrpPrice = 0
            var sellingPrice = 0
            var discountPrice = 0
            var isCartEmpty = false
            try {
                if (it.size > 0) {
                    isCartEmpty = true
                    for (i in 0 until it.size) {
                        finalPrice = it[i].special.drop(1)
                        finalPrice = finalPrice.replace(",", "")
                        cartTotal = it[i].price.drop(1)
                        cartTotal = cartTotal.replace(",", "")
                        mrpPrice += (Integer.parseInt(cartTotal)) * (it[i].quantity)
                        sellingPrice += (Integer.parseInt(finalPrice)) * (it[i].quantity)

                        discountPrice += mrpPrice - sellingPrice
                    }

                    cartTotal = mrpPrice.toString()
                    finalPrice = sellingPrice.toString()
                    savings = discountPrice.toString()

                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            CartPage(
                productViewModel = productViewModel,
                navController,
                data,
                cartTotal,
                savings,
                finalPrice,
                isCartEmpty
            )

        }

    }
}


@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun ProductList(productList: ProductResponse, navController: NavController, activity: Activity) {
    println("Hello  " + productList)
    val scrollState = rememberScrollState()
    Column() {

        TopAppBar(backgroundColor = AppBar,
            title = {
                Text(
                    text = "Lubs Boutique",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif, color = TextColor
                )
            },
            actions = {
                IconButton(onClick = { navController.navigate("cart_screen") }) {
                    Icon(Icons.Filled.ShoppingCart, null, tint = TextColor)
                }
            }
        )

        ConnectivityStatus()

        Spacer(modifier = Modifier.padding(4.dp))

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


@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    // AnimationEffect
    ConnectivityStatus()

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
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "Logo",
            modifier = Modifier
                .scale(scale.value)
                .size(300.dp, 300.dp)
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@Composable
fun ConnectivityStatus() {
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    var visibility by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = visibility,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        ConnectivityStatusBox(isConnected = isConnected)
    }

    LaunchedEffect(isConnected) {
        if (!isConnected) {
            visibility = true
        } else {
            delay(2000)
            visibility = false
        }
    }
}

@Composable
fun ConnectivityStatusBox(isConnected: Boolean) {
    val backgroundColor by animateColorAsState(if (isConnected) green else red)
    val message = if (isConnected) "Back Online!" else "No Internet Connection!"
    val iconResource = if (isConnected) {
        R.drawable.ic_connected
    } else {
        R.drawable.ic_no_connection
    }

    Box(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painterResource(id = iconResource), "Connectivity Icon", tint = Color.White)
            Spacer(modifier = Modifier.size(8.dp))
            Text(message, color = Color.White, fontSize = 15.sp)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LubsBoutiqueTheme {

    }
}