package vn.vngalaxy.fas.data.repository

import vn.vngalaxy.fas.model.Building
import vn.vngalaxy.fas.shared.scheduler.DataResult

interface BuildingRepository {
    suspend fun getAllBuilding(): DataResult<List<Building>>

    suspend fun getAllBuildingLocal(): DataResult<List<Building>>

    suspend fun insertMultiBuildingsLocal(buildings: List<Building>): DataResult<Any>
}