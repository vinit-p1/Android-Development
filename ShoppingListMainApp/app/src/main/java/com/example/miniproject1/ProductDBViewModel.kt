package com.example.miniproject1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniproject1.Model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProductDBViewModel(private val app: Application) : AndroidViewModel(app){

    private val productRepository: ProductRepository
    val product: Flow<List<Product>>

    init {
        val productDao = ProductDatabase.getDatabase(app).productDao()
        productRepository = ProductRepository(productDao)
        product = productRepository.allProduct
    }

    fun insertProduct(product: Product){
        viewModelScope.launch {
            productRepository.insert(product)
        }
    }

    fun updateProduct(product: Product){
        viewModelScope.launch {
            productRepository.update(product)
        }
    }

    fun deleteProduct(product: Product){
        viewModelScope.launch {
            productRepository.delete(product)
        }
    }
}