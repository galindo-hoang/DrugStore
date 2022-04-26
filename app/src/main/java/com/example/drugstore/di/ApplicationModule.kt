package com.example.drugstore.di

import android.content.Context
import androidx.room.Room
import com.example.drugstore.R
import com.example.drugstore.data.firebase.FirebaseClass
import com.example.drugstore.data.local.dao.CartProductDao
import com.example.drugstore.data.local.dao.PrescriptionDao
import com.example.drugstore.data.local.dao.PrescriptionDetailDao
import com.example.drugstore.data.local.database.CartProductDatabase
import com.example.drugstore.data.local.database.MedicineDatabase
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideGoogleSignInClient(
        @ApplicationContext context: Context
    ): GoogleSignInClient =
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.resources.getString(R.string.client_id))
                .requestEmail()
                .build()
        )

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance("https://drugstore-bda06-default-rtdb.asia-southeast1.firebasedatabase.app/")

    @Provides
    fun provideFirebaseClass(googleSignInClient: GoogleSignInClient): FirebaseClass =
        FirebaseClass(googleSignInClient)

    @Provides
    @Singleton
    fun provideMedicineDatabase(@ApplicationContext context: Context): MedicineDatabase =
        Room.databaseBuilder(
            context,
            MedicineDatabase::class.java, "MedicineDatabase"
        ).build()

    @Provides
    fun providePrescriptionDao(medicineDatabase: MedicineDatabase): PrescriptionDao =
        medicineDatabase.prescriptionDao()

    @Provides
    fun providePrescriptionDetailDao(medicineDatabase: MedicineDatabase): PrescriptionDetailDao =
        medicineDatabase.prescriptionDetailDao()

    @Provides
    @Singleton
    fun provideCartProductDatabase(@ApplicationContext context: Context): CartProductDatabase =
        Room.databaseBuilder(
            context,
            CartProductDatabase::class.java,
            "CartProductDatabase"
        ).build()

    @Provides
    fun provideCartProductDao(cartProductDatabase: CartProductDatabase): CartProductDao =
        cartProductDatabase.getCartProductDao()
}
