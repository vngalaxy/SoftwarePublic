package vn.vngalaxy.fas.data.di

import com.google.gson.Gson
import org.koin.dsl.module
import vn.vngalaxy.fas.data.repository.BuildingRepository
import vn.vngalaxy.fas.data.repository.BuildingRepositoryImpl
import vn.vngalaxy.fas.data.repository.FloorRepository
import vn.vngalaxy.fas.data.repository.FloorRepositoryImpl
import vn.vngalaxy.fas.data.repository.MainBuildingRepository
import vn.vngalaxy.fas.data.repository.MainBuildingRepositoryImpl
import vn.vngalaxy.fas.data.repository.MovieRepository
import vn.vngalaxy.fas.data.repository.MovieRepositoryImpl
import vn.vngalaxy.fas.data.repository.RoomRepository
import vn.vngalaxy.fas.data.repository.RoomRepositoryImpl
import vn.vngalaxy.fas.data.repository.SensorRepository
import vn.vngalaxy.fas.data.repository.SensorRepositoryImpl
import vn.vngalaxy.fas.data.repository.UserRepository
import vn.vngalaxy.fas.data.repository.UserRepositoryImpl

val repositoryModule = module {
    single { Gson() }

    single<MovieRepository> {
        MovieRepositoryImpl(get())
    }

    single<BuildingRepository> {
        BuildingRepositoryImpl(get(), get())
    }

    single<MainBuildingRepository> {
        MainBuildingRepositoryImpl(get())
    }

    single<UserRepository> {
        UserRepositoryImpl(get(), get())
    }

    single<FloorRepository> {
        FloorRepositoryImpl(get())
    }

    single<RoomRepository> {
        RoomRepositoryImpl(get())
    }

    single<SensorRepository> {
        SensorRepositoryImpl(get())
    }
}
