package com.lubnamariyam.lubsboutique.view

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.base.R
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.lubnamariyam.lubsboutique.viewModel.ProductViewModel

@Composable
fun CartPage(productViewModel: ProductViewModel ){
    var value by remember { mutableStateOf(5) }
    //var data = productViewModel.getAllProduct().observeAsState(arrayListOf())

    Card(modifier = Modifier
        .padding(8.dp, 4.dp)
        .fillMaxWidth()
        .height(200.dp), shape = RoundedCornerShape(8.dp), elevation = 4.dp) {
        Surface() {
            Row(
                Modifier
                    .padding(4.dp)
                    .fillMaxSize()) {

                Image(
                    painter = rememberImagePainter(
                        data = "",

                        builder = {
                            scale(Scale.FIT)
                            placeholder(R.drawable.notification_action_background)
                            transformations()
                        }
                    ),
                    contentDescription = "image",
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f)
                )
                Column() {
                    Text(text = "Prod Title")
                    Text(text = "1234")
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                ) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(Icons.Filled.Add,
                        contentDescription = "Minus",
                        modifier = Modifier
                            .clickable {
                                value--
                            }
                            .size(25.dp),
                        tint = Color.White
                    )
                    Text(
                        text = "$value",
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp),
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Add",
                        modifier = Modifier
                            .clickable {
                                value++
                            }
                            .size(25.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }

            }
        }
    }
}