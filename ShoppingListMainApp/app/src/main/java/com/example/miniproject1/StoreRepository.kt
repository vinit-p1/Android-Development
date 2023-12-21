package com.example.miniproject1

import com.example.miniproject1.Model.Store

class StoreRepository(private val storeDAO: StoreDAO) {

    companion object {
        private var storeInstance: StoreRepository? = null
        fun instance(storeDAO: StoreDAO? = null): StoreRepository? {
            if (storeInstance == null) {
                if (storeDAO != null) {
                    storeInstance = StoreRepository(storeDAO)
                    return storeInstance
                }
                return null
            }
            return storeInstance
        }
    }
    val allStores = storeDAO.getStores()

    suspend fun insert(store: Store) = storeDAO.insertItem(store)
    suspend fun update(store: Store) = storeDAO.updateItem(store)
    suspend fun delete(store: Store) = storeDAO.deleteItem(store)
    suspend fun deleteItem(id: Long) = storeDAO.deleteItem(id)
    fun getItem(id: Long) = storeDAO.getItem(id)

}