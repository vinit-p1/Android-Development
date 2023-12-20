package com.example.miniproject1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.miniproject1.Model.Product
import com.example.miniproject1.Model.Store

@Database(entities = [Product::class, Store::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDAO
    abstract fun storeDao(): StoreDAO

    companion object{
        private var instance: ProductDatabase? = null

        fun getDatabase(context: Context): ProductDatabase{
            if(instance!=null){
                return instance as ProductDatabase
            }
            instance = Room.databaseBuilder(
                context,
                ProductDatabase::class.java,
                "Product Database"
            ).build()
            return instance as ProductDatabase
        }
    }

}