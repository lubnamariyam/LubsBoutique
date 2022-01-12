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

    fun getSingleAllProduct(id:String): LiveData<ProductEntity> {
        return productRepository.readSingleData(id)
    }


    fun insertProduct(productEntity: ProductEntity) {
        viewModelScope.launch {
            productRepository.insertProduct(productEntity)
        }
    }


    fun updateProductQuantity(productId: String, quantity: Int) = viewModelScope.launch {
        productRepository.updateProductCount(productId, quantity)
    }

    fun deleteProduct(productId: String) = viewModelScope.launch {
        productRepository.deleteCartProduct(productId)
    }
}