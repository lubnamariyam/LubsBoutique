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

    fun readSingleData(id : String) : LiveData<ProductEntity> =  productDao.getSingleAll(id)


    suspend fun insertProduct(productEntity: ProductEntity) {
        productDao.insert(productEntity)
    }


    suspend fun updateProductCount(productId: String, quantity: Int) {
        productDao.updateCartProduct(productId, quantity)
    }

    suspend fun deleteCartProduct(productId: String) {
        productDao.deleteCartProduct(productId)
    }
}