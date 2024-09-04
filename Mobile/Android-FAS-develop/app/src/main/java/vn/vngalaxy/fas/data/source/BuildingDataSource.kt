package vn.vngalaxy.fas.data.source

import vn.vngalaxy.fas.model.Building

interface BuildingDataSource {

    interface Local {
        suspend fun getAllBuildings(): List<Building>

        suspend fun getBuildingById(id: String): Building?

        suspend fun insertBuilding(building: Building)

        suspend fun insertMultiBuildings(buildings: List<Building>)

        suspend fun deleteBuilding(building: Building)

        suspend fun deleteAllBuildings()
    }

    interface Remote {
        suspend fun getAllBuildings(): List<Building>

        suspend fun getBuildingById(id: String): Building?

        suspend fun insertBuilding(building: Building)

        suspend fun deleteBuilding(building: Building)

        suspend fun deleteAllBuildings()
    }
}