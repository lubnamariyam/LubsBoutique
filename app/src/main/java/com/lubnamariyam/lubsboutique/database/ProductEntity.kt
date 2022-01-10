package com.lubnamariyam.lubsboutique.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class ProductEntity(

    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "price")
    val price: String,
    @ColumnInfo(name = "quantity")
    val quantity: Int,
    @ColumnInfo(name = "special")
    val special: String,
    @ColumnInfo(name = "product_id")
    val product_id: String,
    @ColumnInfo(name = "description")
    val description: String
){
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0
}
