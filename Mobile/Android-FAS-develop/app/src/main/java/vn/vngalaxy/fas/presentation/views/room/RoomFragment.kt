package vn.vngalaxy.fas.presentation.views.room

import android.view.LayoutInflater
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.databinding.FragmentRoomBinding
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.presentation.base.BaseFragment
import vn.vngalaxy.fas.presentation.views.room.adapter.ApartmentAdapter
import vn.vngalaxy.fas.presentation.views.room.adapter.FloorsAdapter
import vn.vngalaxy.fas.shared.extensions.navigate
import vn.vngalaxy.fas.shared.extensions.setOnClick

class RoomFragment : BaseFragment<FragmentRoomBinding, RoomViewModel>() {
    override val viewModel: RoomViewModel by viewModel()

    private var floorsAdapter = FloorsAdapter(::onClickFloorItem)
    private var apartmentAdapter = ApartmentAdapter(::onClickApartmentItem, ::onRoomItemClick)

    private fun onClickApartmentItem(apartment: Apartment) {
        navigate(RoomFragmentDirections.actionToEditApartment(apartment))
    }

    private fun onRoomItemClick(room: Room, apartment: Apartment) {
        navigate(RoomFragmentDirections.actionToEditRoom(room))
    }

    private fun onClickFloorItem(floor: Floor) {
        if (floor == floorsAdapter.defaultItem) {
            viewModel.loadApartmentDefault()
        } else {
            viewModel.loadApartmentByFloor(floor)
        }
    }

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentRoomBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.floorList.adapter = floorsAdapter
        viewBinding.apartmentList.adapter = apartmentAdapter
    }

    override fun bindView() {}

    override fun registerLiveData() {
        super.registerLiveData()
        with(viewModel) {
            floors.observe(viewLifecycleOwner) {
                val currentList = it.toMutableList()
                currentList.add(0, floorsAdapter.defaultItem)
                floorsAdapter.submitList(currentList)
                loadApartmentDefault()
            }

            apartments.observe(viewLifecycleOwner) {
                apartmentAdapter.submitList(it)
            }
        }
    }

    override fun handleEvent() {
        with(viewBinding) {
            btnEditBuilding.setOnClick {
                navigate(RoomFragmentDirections.actionToEditBuilding())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadAllFloors()
    }
}

