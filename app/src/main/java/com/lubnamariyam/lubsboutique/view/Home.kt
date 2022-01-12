package com.lubnamariyam.lubsboutique.view

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.lubnamariyam.lubsboutique.MainActivity
import com.lubnamariyam.lubsboutique.model.Product
import com.lubnamariyam.lubsboutique.ui.theme.TextColor


@Composable
fun HomeScreen(navController: NavController, activity: Activity, productList: Product) {
    BackHandler() {
        val alertDialogBuilder = AlertDialog.Builder(activity)
        alertDialogBuilder.setTitle("Exit App")
        alertDialogBuilder.setMessage("Are you sure you want to exit?")
        alertDialogBuilder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            activity.finish()
        }
        alertDialogBuilder.setNegativeButton("No", { dialogInterface: DialogInterface, i: Int -> })

        alertDialogBuilder.create()
        alertDialogBuilder.show()
    }

    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .width(200.dp)
            .height(275.dp), shape = RoundedCornerShape(8.dp), elevation = 4.dp
    ) {
        Surface() {
            Column(
                Modifier
                    .padding(4.dp)
                    .fillMaxSize().background(Color.White)
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

                Text(
                    text = productList.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 4.dp),
                    fontFamily = FontFamily.SansSerif, color = TextColor
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Row(modifier = Modifier.padding(start = 4.dp)) {
                    Text(
                        text = productList.special,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        fontFamily = FontFamily.SansSerif, color = TextColor
                    )
                    Spacer(modifier = Modifier.padding(6.dp))
                    Text(
                        text = productList.price,
                        color = TextColor,
                        textAlign = TextAlign.End,
                        style = TextStyle(textDecoration = TextDecoration.LineThrough),
                        modifier = Modifier.padding(top = 2.dp),
                        fontFamily = FontFamily.SansSerif
                    )
                }
                Spacer(modifier = Modifier.padding(4.dp))
                Button(
                    onClick = {
                        MainActivity.tempProduct = productList
                        navController.navigate("product_detail")
                    },
                    modifier = Modifier
                        .padding(0.dp)
                        .fillMaxWidth(),
                    enabled = true, shape = MaterialTheme.shapes.medium,
                ) {
                    Text(text = "BUY", color = TextColor)
                }
            }
        }
    }

}