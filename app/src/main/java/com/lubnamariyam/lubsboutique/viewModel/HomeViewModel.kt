package com.lubnamariyam.lubsboutique.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lubnamariyam.lubsboutique.model.Product
import com.lubnamariyam.lubsboutique.model.ProductResponse
import com.lubnamariyam.lubsboutique.network.RetrofitService
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    companion object{
        var productListResponse : ProductResponse? = null
    }

    var products = Product("","","","", listOf(),"", listOf(),"","",0,"","","","")
    var productListResponse: ProductResponse by mutableStateOf(ProductResponse(listOf()))
    var errorMessage: String by mutableStateOf("")
    fun getProductList() {
        viewModelScope.launch {
            val apiService = RetrofitService.ApiService.getInstance()
            try {
                val productList = apiService.getProduct()
                productListResponse = productList
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}