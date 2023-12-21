package com.example.miniproject1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniproject1.Model.Store
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class StoreListViewModel(private val app: Application) : AndroidViewModel(app) {
    private val storeRepository: StoreRepository
    val store: Flow<List<Store>>

    init {
        val storeDAO = ProductDatabase.getDatabase(app).storeDao()
        storeRepository = StoreRepository.instance(storeDAO = storeDAO) !!
        store = storeRepository.allStores

    }

    fun insertStore(store: Store):Long {
        var id:Long = -11
        viewModelScope.launch {
            id = storeRepository.insert(store)
        }
        return id
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
    fun deleteStore(id: Long) {
        viewModelScope.launch {
            storeRepository.deleteItem(id)
        }
    }

}