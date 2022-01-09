package com.lubnamariyam.lubsboutique.view

import android.app.Activity
import android.graphics.fonts.Font
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import coil.compose.base.R
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.lubnamariyam.lubsboutique.MainActivity
import com.lubnamariyam.lubsboutique.model.Product
import com.lubnamariyam.lubsboutique.model.ProductResponse
import com.lubnamariyam.lubsboutique.viewModel.HomeViewModel

@Composable
fun HomeScreen(navController: NavController , activity: Activity , productList : Product){
    BackHandler() {
        activity.finish()
    }
    Card(modifier = Modifier
        .padding(8.dp, 4.dp)
        .width(200.dp)
        .height(275.dp), shape = RoundedCornerShape(8.dp), elevation = 4.dp) {
        Surface() {
            Column(
                Modifier
                    .padding(4.dp)
                    .fillMaxSize()
            ) {

                Image(
                    painter = rememberImagePainter(
                        data = productList.image,

                        builder = {
                            scale(Scale.FIT)
                            placeholder(coil.compose.base.R.drawable.notification_action_background)
                            transformations()
                        }
                    ),
                    contentDescription = "image",
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f)
                )

                Text(text = productList.name , maxLines = 1, overflow = TextOverflow.Ellipsis , modifier = Modifier.padding(start = 4.dp), fontFamily = FontFamily.SansSerif
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Row(modifier = Modifier.padding(start = 4.dp)) {
                    Text(text = productList.special, fontWeight = FontWeight.Bold , textAlign = TextAlign.Start, fontFamily = FontFamily.SansSerif)
                    Spacer(modifier = Modifier.padding(6.dp))
                    Text(text = productList.price, color = Color.Gray , textAlign = TextAlign.End , style = TextStyle(textDecoration = TextDecoration.LineThrough), modifier = Modifier.padding(top = 2.dp), fontFamily = FontFamily.SansSerif)
                }
                Spacer(modifier = Modifier.padding(4.dp))
                Button(onClick = {
                                 MainActivity.tempProduct = productList
                    navController.navigate("product_detail")
                }, modifier = Modifier
                    .padding(0.dp)
                    .fillMaxWidth(), enabled = true ,shape = MaterialTheme.shapes.medium,) {
                    Text(text = "BUY", color = Color.White)
                }
                
                


            }
        }
    }
}