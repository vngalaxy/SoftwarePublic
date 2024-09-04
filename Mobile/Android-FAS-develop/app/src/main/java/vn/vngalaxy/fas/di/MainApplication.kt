package vn.vngalaxy.fas.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import vn.vngalaxy.fas.data.di.dataBaseModule
import vn.vngalaxy.fas.data.di.dataSourceModule
import vn.vngalaxy.fas.data.di.networkModule
import vn.vngalaxy.fas.data.di.repositoryModule
import vn.vngalaxy.fas.data.di.serviceModule
import vn.vngalaxy.fas.presentation.di.viewModelModule

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val modules = listOf(
            networkModule,
            repositoryModule,
            serviceModule,
            dataBaseModule,
            dataSourceModule,
            viewModelModule,
        )
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MainApplication)
            modules(modules)
        }
    }
}