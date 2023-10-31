package com.yukle.qrcodescanner.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.yukle.qrcodescanner.data.local.AppDatabase
import com.yukle.qrcodescanner.data.netwrok.service.QrCodeService
import com.yukle.qrcodescanner.data.netwrok.serviceImp.QrCodeServiceImpl
import com.yukle.qrcodescanner.domain.repository.QrCodeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tm.belet.films.data.netwrok.AuthInterceptor
import tm.belet.films.data.netwrok.KtorClient
import tm.belet.films.data.repository.QrCodeRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }


    @Singleton
    @Provides
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "QrCode")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Singleton
    @Provides
    fun provideKtorClient(authInterceptor: AuthInterceptor): KtorClient {
        return KtorClient(authInterceptor)
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor2(context: Context): AuthInterceptor =
        AuthInterceptor(context)

    @Singleton
    @Provides
    fun provideQrCodeService(httpClient: KtorClient): QrCodeService {
        return QrCodeServiceImpl(httpClient)
    }


//________________________________________________________________________________________________________________________


    @Singleton
    @Provides
    fun provideQrCodeRepository(paymentService: QrCodeService): QrCodeRepository {
        return QrCodeRepositoryImpl(paymentService)
    }

}