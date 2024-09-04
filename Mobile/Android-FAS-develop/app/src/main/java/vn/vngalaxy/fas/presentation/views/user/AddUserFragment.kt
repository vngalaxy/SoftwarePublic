package vn.vngalaxy.fas.presentation.views.user

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.R
import vn.vngalaxy.fas.databinding.FragmentAddUserBinding
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.presentation.base.BaseFragment
import vn.vngalaxy.fas.shared.constant.Constant
import vn.vngalaxy.fas.shared.extensions.isValidPhoneNumber
import vn.vngalaxy.fas.shared.extensions.navigateBack
import vn.vngalaxy.fas.shared.extensions.setOnClick
import vn.vngalaxy.fas.shared.extensions.updateAppearance
import vn.vngalaxy.fas.shared.scheduler.DataResult
import vn.vngalaxy.fas.shared.widget.showFailedSnackbar
import vn.vngalaxy.fas.shared.widget.showSuccessSnackbar

class AddUserFragment : BaseFragment<FragmentAddUserBinding, AddUserViewModel>() {
    override val viewModel: AddUserViewModel by viewModel()
    private val args: AddUserFragmentArgs by navArgs()

    //Dropdown adapter
    private lateinit var floorAdapter: ArrayAdapter<Floor>
    private lateinit var apartmentAdapter: ArrayAdapter<Apartment>
    private lateinit var roomAdapter: ArrayAdapter<Room>

    private var apartmentList = mutableListOf<Apartment>()
    private var roomList = mutableListOf<Room>()

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentAddUserBinding.inflate(inflater)

    override fun setUpView() {
        floorAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, args.listFloor.floors)
        viewBinding.floorsAutoTxt.setAdapter(floorAdapter)
    }

    override fun bindView() {

    }

    override fun registerLiveData() {
        super.registerLiveData()
        with(viewModel) {
            selectedFloor.observe(viewLifecycleOwner) {
                apartmentList = it.apartments.toMutableList()
                apartmentAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, apartmentList)
                viewBinding.apartmentAutoTxt.setAdapter(apartmentAdapter)
                viewBinding.floorsAutoTxt.setText(it.name, false)
                viewBinding.btnAddUser.updateAppearance(false)
            }

            selectedApartment.observe(viewLifecycleOwner) {
                roomList = it.rooms.toMutableList()
                roomAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, roomList)
                viewBinding.roomAutoTxt.setAdapter(roomAdapter)
                viewBinding.apartmentAutoTxt.setText(it.name, false)
                viewBinding.btnAddUser.updateAppearance(false)
            }

            selectedRoom.observe(viewLifecycleOwner) {
                viewBinding.roomAutoTxt.setText(it.name, false)
                viewBinding.btnAddUser.updateAppearance(userName.isNotEmpty() && phoneNumber.isValidPhoneNumber())
            }

            addUserResult.observe(viewLifecycleOwner) {
                when (it) {
                    is DataResult.Success -> {
                        viewBinding.btnAddUser.updateAppearance(false)
                        view?.showSuccessSnackbar(getString(R.string.add_user_success))
                        viewModel.reset()
                    }

                    is DataResult.Error -> {
                        view?.showFailedSnackbar(it.exception.message.toString())
                        reset()
                    }

                    is DataResult.Loading -> Unit
                    DataResult.Empty -> hideLoading()
                }
            }
        }
    }

    override fun handleEvent() {
        with(viewBinding) {
            btnBack.setOnClick {
                navigateBack()
            }

            btnAddUser.setOnClick {
                viewModel.createUser()
            }

            floorsAutoTxt.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedFloor = args.listFloor.floors[position]
                viewModel.setSelectedFloor(selectedFloor)

                // Reset room
                apartmentAutoTxt.setText(Constant.STRING_EMPTY)
                roomAutoTxt.setText(Constant.STRING_EMPTY)

            }

            apartmentAutoTxt.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedApartment = apartmentList[position]
                viewModel.setSelectedApartment(selectedApartment)

                // Reset room
                roomAutoTxt.setText(Constant.STRING_EMPTY)
            }

            roomAutoTxt.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedRoom = roomList[position]
                viewModel.setSelectedRoom(selectedRoom)
            }

            phoneNumberEdt.addTextChangedListener(textWatcher)
            userNameEdt.addTextChangedListener(textWatcher)
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            with(viewModel) {
                phoneNumber = viewBinding.phoneNumberEdt.text.toString().trim()
                userName = viewBinding.userNameEdt.text.toString().trim()
                viewBinding.btnAddUser.updateAppearance(
                    selectedRoom.value != null &&
                            phoneNumber.isValidPhoneNumber() &&
                            userName.isNotEmpty()
                )
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }
}