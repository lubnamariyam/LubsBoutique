package com.lubnamariyam.lubsboutique.view

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.base.R
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.lubnamariyam.lubsboutique.ui.theme.Purple200
import com.lubnamariyam.lubsboutique.viewModel.ProductViewModel

@Composable
fun CartPage(productViewModel: ProductViewModel) {
    var data = productViewModel.getAllProduct().observeAsState(arrayListOf())

    Column() {
        TopAppBar(backgroundColor = Color.White,
            title = {
                Text(
                    text = "Cart",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif, color = Purple200
                )
            } ,
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.ArrowBack, null ,
                    tint = Purple200)
                }
            },

            )
        LazyColumn(content = {
            items(
                items = data.value,
                itemContent = {
                    Card(modifier = Modifier
                        .padding(8.dp, 4.dp)
                        .fillMaxWidth()
                        .height(100.dp), shape = RoundedCornerShape(8.dp), elevation = 4.dp) {
                        Surface() {
                            Row() {
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
                                    Text(text = it.name , fontWeight = FontWeight.Medium , textAlign = TextAlign.Start, fontFamily = FontFamily.SansSerif)
                                    Row() {
                                        Text(text = it.special, fontWeight = FontWeight.Bold , textAlign = TextAlign.Start, fontFamily = FontFamily.SansSerif)
                                        Spacer(modifier = Modifier.padding(6.dp))
                                        Text(text = it.price, fontWeight = FontWeight.Light , textAlign = TextAlign.Start, fontFamily = FontFamily.SansSerif,style = TextStyle(textDecoration = TextDecoration.LineThrough), modifier = Modifier.padding(top = 2.dp))
                                    }

                                    Spacer(modifier = Modifier.padding(10.dp))
                                    Row(Modifier.align(Alignment.End)) {
                                        Icon(
                                            painter = painterResource(id = com.lubnamariyam.lubsboutique.R.drawable.ic_baseline_remove_circle_outline_24),
                                            contentDescription = "Minus",
                                            modifier = Modifier
                                                .clickable {
                                                    if (it.quantity == 1){
                                                        productViewModel.deleteProduct(it.product_id)
                                                    }
                                                    else{
                                                        productViewModel.updateProductQuantity(it.product_id, it.quantity - 1)
                                                    }
                                                }
                                                .size(25.dp),
                                            tint = Purple200
                                        )
                                        Text(
                                            text = it.quantity.toString(),
                                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp),
                                            fontFamily = FontFamily.SansSerif,
                                            fontWeight = FontWeight.Medium,
                                            color = Color.Black
                                        )
                                        Icon(
                                            painter = painterResource(id = com.lubnamariyam.lubsboutique.R.drawable.ic_baseline_add_circle_outline_24),
                                            contentDescription = "Add",
                                            modifier = Modifier
                                                .clickable {
                                                    productViewModel.updateProductQuantity(it.product_id,it.quantity +1)
                                                }
                                                .size(25.dp),
                                            tint = Purple200
                                        )
                                        Spacer(modifier = Modifier.padding(4.dp))
                                    }
                                }
                            }
                        }
                    }

                })
        })
    }



}