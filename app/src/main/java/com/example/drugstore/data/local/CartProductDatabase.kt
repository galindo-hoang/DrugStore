package com.example.drugstore.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.drugstore.data.models.CartProduct

@Database(entities = [CartProduct::class], version = 1)
abstract class CartProductDatabase: RoomDatabase() {
    abstract fun getNoteDao():CartProductDao
    companion object{
        @Volatile
        private var instance:CartProductDatabase? = null
        fun getInstance(context: Context):CartProductDatabase{
            if(instance == null){
                instance = Room.databaseBuilder(context,CartProductDatabase::class.java,"NoteDatabase").build()
            }
            return instance!!
        }
    }
}