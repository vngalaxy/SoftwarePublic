package vn.vngalaxy.fas.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import vn.vngalaxy.fas.model.Building
import vn.vngalaxy.fas.shared.utils.ConverterUntil

@Database(entities = [(Building::class)], version = 1)
@TypeConverters(ConverterUntil::class)
abstract class BuildingDataBase : RoomDatabase() {

    abstract fun getBuildingDao(): BuildingDao
}
