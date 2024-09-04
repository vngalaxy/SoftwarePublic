package vn.vngalaxy.fas.data.di

import org.koin.dsl.module
import vn.vngalaxy.fas.data.source.BuildingDataSource
import vn.vngalaxy.fas.data.source.FloorDataSource
import vn.vngalaxy.fas.data.source.MainBuildingDatasource
import vn.vngalaxy.fas.data.source.RoomDataSource
import vn.vngalaxy.fas.data.source.SensorDataSource
import vn.vngalaxy.fas.data.source.UserDatasource
import vn.vngalaxy.fas.data.source.local.BuildingLocalImpl
import vn.vngalaxy.fas.data.source.local.MainBuildingLocalImpl
import vn.vngalaxy.fas.data.source.local.UserLocalImpl
import vn.vngalaxy.fas.data.source.remote.BuildingRemoteImpl
import vn.vngalaxy.fas.data.source.remote.FloorRemoteImpl
import vn.vngalaxy.fas.data.source.remote.RoomRemoteImpl
import vn.vngalaxy.fas.data.source.remote.SensorRemoteImpl
import vn.vngalaxy.fas.data.source.remote.UserRemoteImpl

val dataSourceModule = module {
    single<BuildingDataSource.Local> {
        BuildingLocalImpl(get())
    }

    single<BuildingDataSource.Remote> {
        BuildingRemoteImpl(get())
    }

    single<MainBuildingDatasource.Local> {
        MainBuildingLocalImpl(get())
    }

    single<UserDatasource.Local> {
        UserLocalImpl(get())
    }

    single<UserDatasource.Remote> {
        UserRemoteImpl(get(), get())
    }

    single<FloorDataSource.Remote> {
        FloorRemoteImpl(get(), get())
    }

    single<RoomDataSource.Remote> {
        RoomRemoteImpl(get(), get())
    }

    single<SensorDataSource.Remote> {
        SensorRemoteImpl(get(), get(), get())
    }
}