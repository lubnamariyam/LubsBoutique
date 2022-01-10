package com.lubnamariyam.lubsboutique.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.lubnamariyam.lubsboutique.database.LubsDatabase
import com.lubnamariyam.lubsboutique.database.ProductDao
import com.lubnamariyam.lubsboutique.database.ProductEntity

class ProductRepository(application: Application) {
    private var productDao: ProductDao

    init {
        val database = LubsDatabase.getDatabase(application)
        productDao = database.productDao()
    }

    val readAllData : LiveData<List<ProductEntity>> =  productDao.getAll()

    suspend fun insertProduct(productEntity: ProductEntity) {
        productDao.insert(productEntity)
    }

    suspend fun deleteProduct(productEntity: ProductEntity) {
        productDao.delete(productEntity)
    }

    suspend fun updateProduct(productEntity: ProductEntity) {
        productDao.update(productEntity)
    }


    suspend fun insertProductById(id: Int) {
        productDao.getById(id)
    }

    suspend fun updateProductCount(productId: String, quantity: Int) {
        productDao.updateCartProduct(productId, quantity)
    }

    suspend fun deleteCartProduct(productId: String) {
        productDao.deleteCartProduct(productId)
    }

    fun getSingleProduct(productId: String) = productDao.getSingleProduct(productId)


    suspend fun deleteProductById(id: Int) {
        productDao.deleteProductById(id)
    }

    suspend fun deleteAllProduct() {
        productDao.deleteAllProduct()
    }

    init {
        val database = LubsDatabase.getDatabase(application)
        productDao = database.productDao()
    }
}