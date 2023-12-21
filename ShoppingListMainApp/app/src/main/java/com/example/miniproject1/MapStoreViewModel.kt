package com.example.miniproject1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniproject1.Model.Store
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MapStoreViewModel(private val app: Application) : AndroidViewModel(app) {

    private val storeRepository: StoreRepository
    val items: Flow<List<Store>>

    init {
        val storeDAO = ProductDatabase.getDatabase(app).storeDao()
        storeRepository = StoreRepository.instance(storeDAO = storeDAO) !!
        items = storeRepository.allStores
    }

    fun getStoreById(id: Long) = storeRepository.getItem(id);

    fun updateItem(store: Store){
        viewModelScope.launch {
            storeRepository.update(store)
        }
    }

}