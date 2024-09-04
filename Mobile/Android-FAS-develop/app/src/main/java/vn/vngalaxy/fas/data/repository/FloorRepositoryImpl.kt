package vn.vngalaxy.fas.data.repository

import vn.vngalaxy.fas.data.source.FloorDataSource
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.shared.extensions.handleException
import vn.vngalaxy.fas.shared.scheduler.DataResult

class FloorRepositoryImpl(
    private val remote: FloorDataSource.Remote,
) : FloorRepository {
    override suspend fun getAllFloors(): DataResult<List<Floor>> {
        return try {
            val response = remote.getAllFloors()
            DataResult.Success(response)
        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }

    override suspend fun getFloorDetail(floor: Floor): DataResult<Floor> {
        return try {
            val response = remote.getFloorDetail(floor)
            DataResult.Success(response)
        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }

    override suspend fun getApartmentDetail(apartment: Apartment): DataResult<Apartment> {
        return try {
            val response = remote.getApartmentDetail(apartment)
            DataResult.Success(response)
        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }

    override suspend fun addFloor(floor: Floor): DataResult<Unit> {
        return try {
            val response = remote.addFloor(floor)
            DataResult.Success(response)
        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }

    override suspend fun addApartment(apartment: Apartment): DataResult<Unit> {
        return try {
            val response = remote.addApartment(apartment)
            DataResult.Success(response)
        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }

    override suspend fun changeFloorName(floor: Floor): DataResult<Unit> {
        return try {
            val response = remote.changeFloorName(floor)
            DataResult.Success(response)
        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }

    override suspend fun changeApartmentName(apartment: Apartment): DataResult<Unit> {
        return try {
            val response = remote.changeApartmentName(apartment)
            DataResult.Success(response)
        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }

    override suspend fun deleteFloor(floor: Floor): DataResult<Unit> {
        return try {
            val response = remote.deleteFloor(floor)
            DataResult.Success(response)
        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }

    override suspend fun deleteApartment(apartment: Apartment): DataResult<Unit> {
        return try {
            val response = remote.deleteApartment(apartment)
            DataResult.Success(response)
        } catch (e: Exception) {
            DataResult.Error(e.handleException())
        }
    }
}