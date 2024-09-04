package vn.vngalaxy.fas.presentation.views.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.BuildConfig
import vn.vngalaxy.fas.R
import vn.vngalaxy.fas.databinding.FragmentSplashBinding
import vn.vngalaxy.fas.model.Role
import vn.vngalaxy.fas.presentation.views.main.MainActivity
import vn.vngalaxy.fas.shared.extensions.navigate

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SplashViewModel by viewModel()

    private val args: SplashFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainActivity.setupBottomNavigationView(viewModel.user.role)
        Log.d("Dataaaaa", "SplashFragment ${args.sensor}")
        viewModel.completedTasks.observe(viewLifecycleOwner) {
            if (it == SplashViewModel.TOTAL_TASK) {
                val action = if (viewModel.user.sessionId.isNotEmpty()) {
                    viewModel.initSubDatabase()
                    if (args.sensor != null) {
                        SplashFragmentDirections.actionToSensorDetail(args.sensor!!)
                    } else {
                        if (viewModel.user.role == Role.ADMIN.value) {
                            // Admin
                            SplashFragmentDirections.actionToSensor()
                        } else {
                            // TODO
                            SplashFragmentDirections.actionToRoom()
                        }
                    }
                } else {
                    if (viewModel.user.isSeenOnboarding) {
                        SplashFragmentDirections.actionToLogin()
                    } else {
                        SplashFragmentDirections.actionSplashToOnboarding()
                    }
                }

                navigate(action, navOptions { popUpTo(R.id.splash) { inclusive = true } })
            }
        }

        viewModel.exception.observe(viewLifecycleOwner) {
            Log.d("Dataaaaaaaaa", it.toString())
        }

        Log.d("BuildConfig", BuildConfig.BASE_URL)
    }
}