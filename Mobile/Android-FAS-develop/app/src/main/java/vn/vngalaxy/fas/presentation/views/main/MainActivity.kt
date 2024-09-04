package vn.vngalaxy.fas.presentation.views.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import vn.vngalaxy.fas.R
import vn.vngalaxy.fas.databinding.ActivityMainBinding
import vn.vngalaxy.fas.model.Role
import vn.vngalaxy.fas.model.Sensor

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bottomNavigationView = binding.bottomNavigation

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment?
        navHostFragment?.navController?.setGraph(R.navigation.nav_graph)

        if (navHostFragment != null) {
            binding.bottomNavigation.setupWithNavController(navHostFragment.navController)
            setUpView(navHostFragment)
        }

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent != null) {
            handleIntent(intent)
        }
    }

    private fun handleIntent(intent: Intent) {
        if (intent.hasExtra("sensor")) {
            val sensor = intent.getParcelableExtra<Sensor>("sensor")
            val startDestinationArgs = Bundle()
            startDestinationArgs.putParcelable("sensor", sensor)

            Log.d("Dataaaaa", "handleIntent $sensor")

            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment?
            navHostFragment?.navController?.apply {
                navigate(R.id.splash, startDestinationArgs)
            }
        }
    }

    private fun setUpView(navHostFragment: NavHostFragment) {
        navHostFragment.navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.sensor, R.id.user, R.id.room,
                R.id.setting -> binding.bottomNavigation.visibility = View.VISIBLE

                else -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
            }
        }
    }

    /**
     * Called to process touch screen events.
     *
     * @param event the touch screen event
     * @return boolean Return true if this event was consumed.
     */
    @SuppressWarnings("ComplexCondition")
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val v = currentFocus
        if (v is EditText) {
            val scoops = IntArray(2)
            v.getLocationOnScreen(scoops)
            val x = event.rawX + v.getLeft() - scoops[0]
            val y = event.rawY + v.getTop() - scoops[1]
            if (event.action == MotionEvent.ACTION_UP &&
                (x < v.getLeft() || x >= v.getRight() || y < v.getTop() || y > v.getBottom())
            ) {
                v.clearFocus()
                hideSoftKeyboard(v)
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun hideSoftKeyboard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        private lateinit var bottomNavigationView: BottomNavigationView

        fun setupBottomNavigationView(role: String) {
            when (role) {
                Role.ADMIN.value -> {
                    bottomNavigationView.menu.clear()
                    bottomNavigationView.inflateMenu(R.menu.bottom_navigation_admin)
                }

                else -> {
                    bottomNavigationView.menu.clear()
                    bottomNavigationView.inflateMenu(R.menu.bottom_navigation_user)
                }
            }
        }
    }
}