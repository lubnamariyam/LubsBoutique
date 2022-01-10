package com.lubnamariyam.lubsboutique.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
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

    @Query("UPDATE product_table SET quantity=:qty WHERE product_id = :productId")
    suspend fun updateCartProduct(productId: String, qty: Int)

    @Query("DELETE FROM product_table WHERE product_id = :productId")
    suspend fun deleteCartProduct(productId: String)

    @Query("SELECT * FROM product_table WHERE product_id=:product_id")
    fun getSingleProduct(product_id :String) : ProductEntity

    @Query("DELETE FROM product_table where uid = :id")
    suspend fun deleteProductById(id: Int)

    @Query("DELETE FROM product_table")
    suspend fun deleteAllProduct()
}