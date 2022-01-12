package com.lubnamariyam.lubsboutique

import android.app.Activity
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import com.google.accompanist.flowlayout.FlowColumn
import com.lubnamariyam.lubsboutique.Utility.ConnectionState
import com.lubnamariyam.lubsboutique.Utility.connectivityState
import com.lubnamariyam.lubsboutique.model.Product
import com.lubnamariyam.lubsboutique.model.ProductResponse
import com.lubnamariyam.lubsboutique.ui.theme.LubsBoutiqueTheme
import com.lubnamariyam.lubsboutique.ui.theme.Purple200
import com.lubnamariyam.lubsboutique.ui.theme.green
import com.lubnamariyam.lubsboutique.ui.theme.red
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
                TopAppBar(backgroundColor = Color.White,
                    title = {
                        Text(
                            text = "Product Detail",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif, color = Purple200
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate("main_screen") }) {
                            Icon(Icons.Filled.ArrowBack, null, tint = Purple200)
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("cart_screen") }) {
                            Icon(Icons.Filled.ShoppingCart, null, tint = Purple200)
                        }
                    }
                )
                ConnectivityStatus()
                ProductDetailPage(
                    product = MainActivity.tempProduct!!,
                    productViewModel,
                    navController
                )
            }
        }

        // Cart Screen
        composable("cart_screen") {
            var data = productViewModel.getAllProduct().observeAsState(arrayListOf())
            var cartTotal = ""
            var savings = ""
            var finalPrice = ""
            var it = data.value
            var mrpPrice = 0
            var sellingPrice = 0
            var discountPrice = 0
            var isCartEmpty = false
            try {
                if(it.size > 0){
                    isCartEmpty = true
                    for (i in 0 until it.size){
                        finalPrice = it[i].special.drop(1)
                        finalPrice = finalPrice.replace(",","")
                        cartTotal = it[i].price.drop(1)
                        cartTotal = cartTotal.replace(",","")
                        mrpPrice += (Integer.parseInt(cartTotal)) * (it[i].quantity)
                        sellingPrice +=  (Integer.parseInt(finalPrice)) * (it[i].quantity)

                        discountPrice += mrpPrice - sellingPrice
                    }

                    cartTotal = mrpPrice.toString()
                    finalPrice = sellingPrice.toString()
                    savings = discountPrice.toString()

                }

            }catch (e:Exception){
                e.printStackTrace()
            }

            CartPage(productViewModel = productViewModel, navController ,data, cartTotal , savings, finalPrice , isCartEmpty)

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

        TopAppBar(backgroundColor = Color.White,
            title = {
                Text(
                    text = "Lubs Boutique",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif, color = Purple200
                )
            },
            navigationIcon = {
                IconButton(onClick = { activity.finish() }) {
                    Icon(Icons.Filled.ArrowBack, null, tint = Purple200)
                }
            },
            actions = {
                IconButton(onClick = { navController.navigate("cart_screen") }) {
                    Icon(Icons.Filled.ShoppingCart, null, tint = Purple200)
                }
            }
        )

        ConnectivityStatus()

        Spacer(modifier = Modifier.padding(4.dp))

        LazyVerticalGrid(GridCells.Fixed(2)) {
            /*item {
                Card(
                    modifier = Modifier
                        .padding(8.dp, 4.dp)
                        .fillMaxWidth()
                        .height(250.dp), shape = RoundedCornerShape(8.dp), elevation = 4.dp
                ) {

                    Image(
                        painter = painterResource(id = com.lubnamariyam.lubsboutique.R.drawable.banner1),
                        contentDescription = "Live sale",
                        modifier = Modifier.fillMaxSize()
                    )

                }
            }*/
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