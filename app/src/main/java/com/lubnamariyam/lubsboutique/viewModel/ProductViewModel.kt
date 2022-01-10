package com.lubnamariyam.lubsboutique.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.lubnamariyam.lubsboutique.database.ProductEntity
import com.lubnamariyam.lubsboutique.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(appObj: Application) : AndroidViewModel(appObj)  {

    private val productRepository: ProductRepository = ProductRepository(appObj)

    fun getAllProduct(): LiveData<List<ProductEntity>> {
        return productRepository.readAllData
    }

    fun insertProduct(productEntity: ProductEntity) {
        viewModelScope.launch {
            productRepository.insertProduct(productEntity)
        }
    }

    fun deleteProduct(productEntity: ProductEntity) {
        viewModelScope.launch {
            productRepository.deleteProduct(productEntity)
        }
    }
    fun updateProduct(productEntity: ProductEntity) {
        viewModelScope.launch {
            productRepository.updateProduct(productEntity)
        }
    }
    fun insertProductById(id:Int) {
        viewModelScope.launch {
            productRepository.insertProductById(id)
        }
    }

    fun updateProductQuantity(productId: String, quantity: Int) = viewModelScope.launch {
        productRepository.updateProductCount(productId, quantity)
    }

    fun deleteProduct(productId: String) = viewModelScope.launch {
        productRepository.deleteCartProduct(productId)
    }

    fun getSingleProduct(productId: String): ProductEntity {
        return productRepository.getSingleProduct(productId)
    }



}