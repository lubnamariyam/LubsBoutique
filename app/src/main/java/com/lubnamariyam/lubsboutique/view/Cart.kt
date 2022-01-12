package com.lubnamariyam.lubsboutique.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.base.R
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.lubnamariyam.lubsboutique.ConnectivityStatus
import com.lubnamariyam.lubsboutique.MainActivity
import com.lubnamariyam.lubsboutique.database.ProductEntity
import com.lubnamariyam.lubsboutique.ui.theme.TextColor
import com.lubnamariyam.lubsboutique.ui.theme.vlgray
import com.lubnamariyam.lubsboutique.viewModel.ProductViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun CartPage(
    productViewModel: ProductViewModel,
    navController: NavController,
    data: State<List<ProductEntity>>,
    cartTotal: String,
    savings: String,
    finalPrice: String,
    isCartEmpty: Boolean
) {

    if (isCartEmpty) {
        Column(Modifier.background(Color.White)) {
            TopAppBar(
                backgroundColor = com.lubnamariyam.lubsboutique.ui.theme.AppBar,
                title = {
                    Text(
                        text = "Cart",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif, color = TextColor
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (MainActivity.tempProduct != null) {
                            navController.navigate("product_detail")
                        } else {
                            navController.navigate("main_screen")
                        }
                    }) {
                        Icon(
                            Icons.Filled.ArrowBack, null,
                            tint = TextColor
                        )
                    }
                },
            )
            ConnectivityStatus()
            LazyColumn(Modifier.weight(1f), content = {
                items(
                    items = data.value,
                    itemContent = {
                        Card(
                            modifier = Modifier
                                .padding(8.dp, 4.dp)
                                .fillMaxWidth().background(Color.White)
                                .height(100.dp), shape = RoundedCornerShape(8.dp), elevation = 4.dp
                        ) {
                            Surface() {
                                Row(Modifier.background(Color.White)) {
                                    Image(
                                        painter = rememberImagePainter(
                                            data = it.image,
                                            builder = {
                                                scale(Scale.FIT)
                                                placeholder(R.drawable.notification_action_background)
                                                transformations()
                                            }
                                        ),
                                        contentDescription = "image",
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .width(100.dp)
                                    )
                                    Spacer(modifier = Modifier.padding(10.dp))
                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        Text(
                                            text = it.name,
                                            color = TextColor,
                                            fontWeight = FontWeight.Medium,
                                            textAlign = TextAlign.Start,
                                            fontFamily = FontFamily.SansSerif
                                        )
                                        Spacer(modifier = Modifier.padding(4.dp))
                                        Row() {
                                            Text(
                                                text = it.special,
                                                color = TextColor,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Start,
                                                fontFamily = FontFamily.SansSerif
                                            )
                                            Spacer(modifier = Modifier.padding(6.dp))
                                            Text(
                                                text = it.price,
                                                color = TextColor,
                                                fontWeight = FontWeight.Light,
                                                textAlign = TextAlign.Start,
                                                fontFamily = FontFamily.SansSerif,
                                                style = TextStyle(textDecoration = TextDecoration.LineThrough),
                                                modifier = Modifier.padding(top = 2.dp)
                                            )
                                        }

                                        Spacer(modifier = Modifier.padding(10.dp))
                                        Row(Modifier.align(Alignment.End)) {
                                            Icon(
                                                painter = painterResource(id = com.lubnamariyam.lubsboutique.R.drawable.ic_baseline_remove_circle_outline_24),
                                                contentDescription = "Minus",
                                                modifier = Modifier
                                                    .clickable {
                                                        if (it.quantity == 1) {
                                                            productViewModel.deleteProduct(it.product_id)
                                                        } else {
                                                            productViewModel.updateProductQuantity(
                                                                it.product_id,
                                                                it.quantity - 1
                                                            )
                                                        }
                                                    }
                                                    .size(25.dp),
                                                tint = Color.Blue
                                            )
                                            Text(
                                                text = it.quantity.toString(),
                                                modifier = Modifier.padding(
                                                    horizontal = 16.dp,
                                                    vertical = 2.dp
                                                ),
                                                fontFamily = FontFamily.SansSerif,
                                                fontWeight = FontWeight.Medium,
                                                color = TextColor
                                            )
                                            Icon(
                                                painter = painterResource(id = com.lubnamariyam.lubsboutique.R.drawable.ic_baseline_add_circle_outline_24),
                                                contentDescription = "Add",
                                                modifier = Modifier
                                                    .clickable {
                                                        productViewModel.updateProductQuantity(
                                                            it.product_id,
                                                            it.quantity + 1
                                                        )
                                                    }
                                                    .size(25.dp),
                                                tint = Color.Blue
                                            )
                                            Spacer(modifier = Modifier.padding(4.dp))
                                        }
                                    }
                                }
                            }
                        }

                    })

            })
            Spacer(modifier = Modifier.padding(6.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth().background(Color.White)
                    .padding(8.dp, 4.dp), shape = RoundedCornerShape(8.dp), elevation = 4.dp
            ) {
                Column(verticalArrangement = Arrangement.Top , modifier = Modifier.background(Color.White)) {
                    Row(Modifier.background(color = vlgray)) {
                        Text(
                            text = "Price Details", modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp), color = TextColor
                        )
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Column(
                        modifier = Modifier.padding(10.dp).background(Color.White)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Cart Total",
                                color = TextColor,
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily.SansSerif
                            )
                            Text(
                                text = "Rs.${cartTotal.toFloat()}",
                                color = TextColor,
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily.SansSerif
                            )
                        }
                        Spacer(modifier = Modifier.padding(4.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Savings",
                                color = TextColor,
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily.SansSerif
                            )
                            Text(
                                text = "Rs.${savings.toFloat()}",
                                color = TextColor,
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily.SansSerif
                            )
                        }
                        Spacer(modifier = Modifier.padding(4.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Final Price",
                                color = TextColor,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.SansSerif
                            )
                            Text(
                                text = "Rs.${finalPrice.toFloat()}",
                                color = TextColor,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.SansSerif
                            )
                        }
                        Spacer(modifier = Modifier.padding(4.dp))
                    }

                }
            }
            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                onClick = { },
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth(), enabled = true, shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "CHECKOUT", color = TextColor)
            }
        }
    } else {
        Column() {
            TopAppBar(
                backgroundColor = com.lubnamariyam.lubsboutique.ui.theme.AppBar,
                title = {
                    Text(
                        text = "Cart",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif, color = TextColor
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("product_detail") }) {
                        Icon(
                            Icons.Filled.ArrowBack, null,
                            tint = TextColor
                        )
                    }
                },
            )
            ConnectivityStatus()

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = com.lubnamariyam.lubsboutique.R.drawable.emptycart),
                    contentDescription = "empty cart",
                    modifier = Modifier.padding(
                        top = 60.dp, start = 30.dp, end = 30.dp
                    )
                )
                Text(
                    text = "Oops!! Your Cart Is Empty !!",
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.SansSerif,
                    color = TextColor
                )
                Button(
                    onClick = { navController.navigate("main_screen") },
                    modifier = Modifier
                        .padding(start = 100.dp, end = 100.dp, bottom = 6.dp)
                        .fillMaxWidth(), enabled = true, shape = MaterialTheme.shapes.medium
                ) {
                    Text(text = "SHOP NOW", color = TextColor)
                }
            }
        }


    }


}