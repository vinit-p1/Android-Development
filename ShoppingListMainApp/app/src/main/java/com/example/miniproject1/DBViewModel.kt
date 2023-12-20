package com.example.miniproject1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniproject1.Model.Product
import com.example.miniproject1.Model.Store
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DBViewModel(private val app: Application) : AndroidViewModel(app){

    private val productRepository: ProductRepository
    private val storeRepository: StoreRepository

    val product: Flow<List<Product>>
    val store: Flow<List<Store>>
    init {
        val productDao = ProductDatabase.getDatabase(app).productDao()
        val storeDAO = ProductDatabase.getDatabase(app).storeDao()

        productRepository = ProductRepository(productDao)
        storeRepository = StoreRepository(storeDAO)

        product = productRepository.allProduct
        store = storeRepository.allStores
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

    fun insertStore(store: Store) {
        viewModelScope.launch {
            storeRepository.insert(store)
        }
    }

    fun updateStore(store: Store) {
        viewModelScope.launch {
            storeRepository.update(store)
        }
    }

    fun deleteStore(store: Store) {
        viewModelScope.launch {
            storeRepository.delete(store)
        }
    }
}