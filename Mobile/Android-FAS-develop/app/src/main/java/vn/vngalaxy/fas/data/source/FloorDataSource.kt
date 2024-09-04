package vn.vngalaxy.fas.data.source

import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Floor

interface FloorDataSource {
    interface Remote {
        suspend fun getAllFloors(): List<Floor>

        suspend fun getFloorDetail(floor: Floor): Floor

        suspend fun getApartmentDetail(apartment: Apartment): Apartment

        suspend fun addFloor(floor: Floor)

        suspend fun addApartment(apartment: Apartment)

        suspend fun changeFloorName(floor: Floor)

        suspend fun changeApartmentName(apartment: Apartment)

        suspend fun deleteFloor(floor: Floor)

        suspend fun deleteApartment(apartment: Apartment)
    }
}