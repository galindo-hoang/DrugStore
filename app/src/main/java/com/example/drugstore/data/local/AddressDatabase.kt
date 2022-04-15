package com.example.drugstore.data.local

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.drugstore.data.models.Address

@Database(entities = [Address::class], version = 1)
abstract class AddressDatabase : RoomDatabase(){
    abstract fun getAddressDao():AddressDao
    companion object{
        @Volatile
        private var instance: AddressDatabase? = null
        fun getInstance(context: Context): AddressDatabase{
            if(instance == null){
                instance = Room.databaseBuilder(context,AddressDatabase::class.java,"AddressDatabase").build()
            }
            return instance!!
        }
    }
}