package com.example.miniproject1

import com.example.miniproject1.Model.Product


class ProductRepository(private val productDao: ProductDAO) {

    val allProduct = productDao.getProducts()

    suspend fun insert(product: Product) = productDao.insertProduct(product)
    suspend fun update(product: Product) = productDao.updateProduct(product)
    suspend fun delete(product: Product) = productDao.deleteProduct(product)
}