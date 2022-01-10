package com.lubnamariyam.lubsboutique.database

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface ProductDao {
    @Query("SELECT * from product_table")
    fun getAll(): LiveData<List<ProductEntity>>

    @Query("SELECT * from product_table where uid = :id")
    fun getById(id: Int) : ProductEntity?

    @Insert
    suspend fun insert(product:ProductEntity)

    @Update
    suspend fun update(product: ProductEntity)

    @Delete
    suspend fun delete(product: ProductEntity)

    @Query("DELETE FROM product_table where uid = :id")
    suspend fun deleteProductById(id: Int)

    @Query("DELETE FROM product_table")
    suspend fun deleteAllProduct()
}