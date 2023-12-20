package com.example.miniproject1

import com.example.miniproject1.Model.Store

class StoreRepository(private val storeDAO: StoreDAO) {

    val allStores = storeDAO.getStores()

    suspend fun insert(store: Store) = storeDAO.insertItem(store)
    suspend fun update(store: Store) = storeDAO.updateItem(store)
    suspend fun delete(store: Store) = storeDAO.deleteItem(store)

}