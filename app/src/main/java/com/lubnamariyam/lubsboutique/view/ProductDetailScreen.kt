package com.lubnamariyam.lubsboutique.view

import android.hardware.camera2.params.ColorSpaceTransform
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.base.R
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.lubnamariyam.lubsboutique.MainActivity
import com.lubnamariyam.lubsboutique.database.ProductEntity
import com.lubnamariyam.lubsboutique.model.Product
import com.lubnamariyam.lubsboutique.viewModel.ProductViewModel

@Composable
fun ProductDetailPage(product : Product , productViewModel: ProductViewModel , navController: NavController ) {
    var productEntity = ProductEntity(
        image = product.image,
        name = product.name,
        price = product.price,
        special = product.special,
        quantity = 1,
        product_id = product.product_id,
        description = product.description
    )

    var isCartProduct = false
    try {
        var tempProductEntity = productViewModel.getSingleProduct(productEntity.product_id)
        if (tempProductEntity != null){
            isCartProduct = true
            productEntity = tempProductEntity
        }

    }catch (e:Exception){
        e.printStackTrace()
    }




    Column() {
        Card(modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .fillMaxHeight(), shape = RoundedCornerShape(8.dp), elevation = 6.dp) {
            Surface() {
                Column(
                    Modifier
                        .padding(4.dp)
                        .fillMaxSize()
                ) {

                    Image(
                        painter = rememberImagePainter(
                            data = product.image,

                            builder = {
                                scale(Scale.FIT)
                                placeholder(coil.compose.base.R.drawable.notification_action_background)
                                transformations()
                            }
                        ),
                        contentDescription = "image",
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .weight(0.2f)
                    )
                    Spacer(modifier = Modifier.padding(10.dp))

                    Text(text = product.name,color = Color.Black, modifier = Modifier.padding(start = 8.dp), fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold , fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Row(modifier = Modifier.padding(start = 8.dp)) {
                        Text(text = product.special, fontWeight = FontWeight.Bold , textAlign = TextAlign.Start, fontFamily = FontFamily.SansSerif)
                        Spacer(modifier = Modifier.padding(6.dp))
                        Text(text = product.price, color = Color.Gray , textAlign = TextAlign.End , style = TextStyle(textDecoration = TextDecoration.LineThrough), modifier = Modifier.padding(top = 2.dp), fontFamily = FontFamily.SansSerif)
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(text = "Description :" , textAlign = TextAlign.Start, fontFamily = FontFamily.SansSerif, modifier = Modifier.padding(start = 8.dp) , fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = product.description ,color = Color.Gray, textAlign = TextAlign.Start, fontFamily = FontFamily.SansSerif , modifier = Modifier.padding(start = 8.dp) , lineHeight = 20.sp )
                    Spacer(modifier = Modifier.padding(8.dp))

                    if (isCartProduct){
                        Button(onClick = { navController.navigate("cart_screen") }
                            ,
                            modifier = Modifier
                                .padding(start = 6.dp, end = 6.dp, bottom = 6.dp)
                                .fillMaxWidth(), enabled = true ,shape = MaterialTheme.shapes.medium) {
                            Text(text = "VIEW IN CART", color = Color.White)

                        }
                    }else{
                        Button(onClick = {
                            productViewModel.insertProduct(productEntity)
                            println("Prod-->" + productViewModel.getAllProduct())
                            navController.navigate("cart_screen")
                        },
                            modifier = Modifier
                                .padding(start = 6.dp, end = 6.dp, bottom = 6.dp)
                                .fillMaxWidth(), enabled = true ,shape = MaterialTheme.shapes.medium,) {
                            Text(text = "ADD TO CART", color = Color.White)
                        }
                    }

                }
            }
        }
    }

}

