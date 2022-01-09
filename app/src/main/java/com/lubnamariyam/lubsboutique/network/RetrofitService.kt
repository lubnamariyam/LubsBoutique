package com.lubnamariyam.lubsboutique.network

import com.lubnamariyam.lubsboutique.model.Product
import com.lubnamariyam.lubsboutique.model.ProductResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class RetrofitService {
    interface ApiService {
        @GET("/v2/5def7b172f000063008e0aa2")
        suspend fun getProduct() : ProductResponse


        companion object {
            var apiService: ApiService? = null
            var baseUrl = "https://www.mocky.io"
            fun getInstance() : ApiService {
                if (apiService == null) {
                    apiService = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build().create(ApiService::class.java)
                }
                return apiService!!
            }
        }
    }
}