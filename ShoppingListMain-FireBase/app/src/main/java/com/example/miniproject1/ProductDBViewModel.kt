package com.example.miniproject1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniproject1.Model.Product
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDBViewModel(private val app: Application) : AndroidViewModel(app){

    private val productRepository: ProductRepository
    private val firebaseDatabase: FirebaseDatabase
    val product: StateFlow<HashMap<String, Product>>

    init {
        firebaseDatabase = FirebaseDatabase.getInstance()
        productRepository = ProductRepository(firebaseDatabase)
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

    fun deleteProduct(id: String){
        viewModelScope.launch {
            productRepository.delete(id)
        }
    }
}