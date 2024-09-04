package vn.vngalaxy.fas.data.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import vn.vngalaxy.fas.data.source.remote.appwrite.AppwriteManager

val serviceModule = module {
    single { AppwriteManager(androidContext()) }
}