package vn.vngalaxy.fas.data.di

import android.content.Context
import androidx.room.Room
import org.koin.core.module.Module
import org.koin.dsl.module
import vn.vngalaxy.fas.data.source.local.BuildingDataBase
import vn.vngalaxy.fas.shared.constant.Constant.BUILDING_DATABASE

fun provideBuildingDataBase(context: Context): BuildingDataBase =
    Room.databaseBuilder(
        context,
        BuildingDataBase::class.java,
        BUILDING_DATABASE
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

fun provideBuildingDao(buildingDataBase: BuildingDataBase) = buildingDataBase.getBuildingDao()

val dataBaseModule: Module = module {
    single { provideBuildingDataBase(get()) }
    single { provideBuildingDao(get()) }
}