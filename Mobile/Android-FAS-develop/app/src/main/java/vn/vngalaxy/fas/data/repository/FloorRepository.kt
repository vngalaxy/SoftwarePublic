package vn.vngalaxy.fas.data.repository

import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.shared.scheduler.DataResult

interface FloorRepository {
    suspend fun getAllFloors(): DataResult<List<Floor>>

    suspend fun getFloorDetail(floor: Floor): DataResult<Floor>

    suspend fun getApartmentDetail(apartment: Apartment): DataResult<Apartment>

    suspend fun addFloor(floor: Floor): DataResult<Unit>

    suspend fun addApartment(apartment: Apartment): DataResult<Unit>

    suspend fun changeFloorName(floor: Floor): DataResult<Unit>

    suspend fun changeApartmentName(apartment: Apartment): DataResult<Unit>

    suspend fun deleteFloor(floor: Floor): DataResult<Unit>

    suspend fun deleteApartment(apartment: Apartment): DataResult<Unit>
}