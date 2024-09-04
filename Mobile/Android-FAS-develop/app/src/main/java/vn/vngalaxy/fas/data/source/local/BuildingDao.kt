package vn.vngalaxy.fas.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vn.vngalaxy.fas.model.Building


@Dao
interface BuildingDao {
    @Query("SELECT * FROM buildings")
    suspend fun getAllBuildings(): MutableList<Building>

    @Query("SELECT * FROM buildings WHERE id = :id")
    suspend fun getBuildingById(id: String): Building?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuilding(building: Building)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultiBuildings(buildings: List<Building>)

    @Delete
    suspend fun deleteBuilding(building: Building)

    @Query("DELETE FROM buildings")
    suspend fun deleteAllBuildings()
}
