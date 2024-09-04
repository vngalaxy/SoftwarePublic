package vn.vngalaxy.fas.data.repository

import vn.vngalaxy.fas.data.source.BuildingDataSource
import vn.vngalaxy.fas.model.Building
import vn.vngalaxy.fas.shared.extensions.handleException
import vn.vngalaxy.fas.shared.scheduler.DataResult


class BuildingRepositoryImpl(
    private val local: BuildingDataSource.Local,
    private val remote: BuildingDataSource.Remote,
) : BuildingRepository {
    override suspend fun getAllBuilding(): DataResult<List<Building>> {
        return try {
            val response = remote.getAllBuildings()
            DataResult.Success(response)
        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }

    override suspend fun getAllBuildingLocal(): DataResult<List<Building>> {
        return try {
            val data = local.getAllBuildings()
            DataResult.Success(data)
        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }

    override suspend fun insertMultiBuildingsLocal(buildings: List<Building>): DataResult<Any> {
        return try {
            local.insertMultiBuildings(buildings)
            DataResult.Success(true)
        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }
}