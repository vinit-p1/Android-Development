package com.example.miniproject1

import android.util.Log
import androidx.compose.animation.core.snap
import com.example.miniproject1.Model.Product
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow


class ProductRepository(private val firebaseDatabase: FirebaseDatabase) {

    val allProduct = MutableStateFlow(HashMap<String, Product>())
    private val products: String = "products"

    init {
        firebaseDatabase.getReference(products).addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val product = Product(
                    id = snapshot.ref.key as String,
                    name = snapshot.child("name").value as String,
                    price = (snapshot.child("price").value as? Long)?.toDouble() ?: 0.0,
                    quantity = (snapshot.child("quantity").value as? Long)?.toInt() ?: 0,
                    isPurchased = snapshot.child("isPurchased").value as Boolean,
                    )
                allProduct.value = allProduct.value.toMutableMap().apply {
                    put(product.id, product)
                } as HashMap<String, Product>
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val product = Product(
                    id = snapshot.ref.key as String,
                    name = snapshot.child("name").value as String,
                    price = (snapshot.child("price").value as? Long)?.toDouble() ?: 0.0,
                    quantity = (snapshot.child("quantity").value as? Long)?.toInt() ?: 0,
                    isPurchased = snapshot.child("isPurchased").value as Boolean,

                    )
                allProduct.value = allProduct.value.toMutableMap().apply {
                    put(product.id, product)
                } as HashMap<String, Product>
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val product = Product(
                    id = snapshot.ref.key as String,
                    name = snapshot.child("name").value as String,
                    price = (snapshot.child("price").value as? Long)?.toDouble() ?: 0.0,
                    quantity = (snapshot.child("quantity").value as? Long)?.toInt() ?: 0,
                    isPurchased = snapshot.child("isPurchased").value as Boolean,

                    )
                allProduct.value = allProduct.value.toMutableMap().apply {
                    remove(product.id)
                } as HashMap<String, Product>            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("error", error.message)
            }

        })
    }

    suspend fun insert(product: Product) {
      firebaseDatabase.getReference(products).push().also {
          product.id = it.ref.key.toString()
          it.setValue(product)
      }
    }
    suspend fun update(product: Product) {
        firebaseDatabase.getReference(products+"/${product.id}").child("name").setValue(product.name)
        firebaseDatabase.getReference(products+"/${product.id}").child("price").setValue(product.name)
        firebaseDatabase.getReference(products+"/${product.id}").child("quantity").setValue(product.name)
        firebaseDatabase.getReference(products+"/${product.id}").child("isPurchased").setValue(product.name)
    }
    suspend fun delete(id: String) {
        firebaseDatabase.getReference(products+"/${id}").removeValue()

    }

}