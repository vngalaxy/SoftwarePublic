package vn.vngalaxy.fas.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.vngalaxy.fas.presentation.views.add_common.AddCommonViewModel
import vn.vngalaxy.fas.presentation.views.apartment.EditApartmentViewModel
import vn.vngalaxy.fas.presentation.views.building.EditBuildingViewModel
import vn.vngalaxy.fas.presentation.views.building.SelectBuildingViewModel
import vn.vngalaxy.fas.presentation.views.change_name.ChangeNameDialogViewModel
import vn.vngalaxy.fas.presentation.views.change_room.ChangeRoomDialogViewModel
import vn.vngalaxy.fas.presentation.views.floor.EditFloorViewModel
import vn.vngalaxy.fas.presentation.views.login.LoginViewModel
import vn.vngalaxy.fas.presentation.views.onboarding.OnboardingViewModel
import vn.vngalaxy.fas.presentation.views.otp.OtpViewModel
import vn.vngalaxy.fas.presentation.views.room.EditRoomViewModel
import vn.vngalaxy.fas.presentation.views.room.RoomViewModel
import vn.vngalaxy.fas.presentation.views.sensor.AddSensorViewModel
import vn.vngalaxy.fas.presentation.views.sensor.EditSensorViewModel
import vn.vngalaxy.fas.presentation.views.sensor.QrScanViewModel
import vn.vngalaxy.fas.presentation.views.sensor.SensorDetailViewModel
import vn.vngalaxy.fas.presentation.views.sensor.SensorViewModel
import vn.vngalaxy.fas.presentation.views.setting.SettingViewModel
import vn.vngalaxy.fas.presentation.views.splash.SplashViewModel
import vn.vngalaxy.fas.presentation.views.user.AddUserViewModel
import vn.vngalaxy.fas.presentation.views.user.EditUserViewModel
import vn.vngalaxy.fas.presentation.views.user.UserViewModel

val viewModelModule = module {
    viewModel { SplashViewModel(get(), get(), get(), get()) }
    viewModel { OnboardingViewModel() }
    viewModel { SelectBuildingViewModel(get()) }
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { OtpViewModel(get(), get()) }
    viewModel { SensorViewModel(get(), get()) }
    viewModel { SensorDetailViewModel(get(), get(), get()) }
    viewModel { EditSensorViewModel(get()) }
    viewModel { ChangeRoomDialogViewModel(get(), get()) }
    viewModel { UserViewModel(get(), get()) }
    viewModel { AddUserViewModel(get()) }
    viewModel { EditUserViewModel(get(), get()) }
    viewModel { ChangeNameDialogViewModel(get(), get(), get()) }
    viewModel { AddSensorViewModel(get(), get()) }
    viewModel { QrScanViewModel() }
    viewModel { RoomViewModel(get()) }
    viewModel { EditBuildingViewModel(get(), get()) }
    viewModel { AddCommonViewModel(get(), get()) }
    viewModel { EditFloorViewModel(get()) }
    viewModel { EditApartmentViewModel(get()) }
    viewModel { EditRoomViewModel(get()) }
    viewModel { SettingViewModel(get()) }
}
