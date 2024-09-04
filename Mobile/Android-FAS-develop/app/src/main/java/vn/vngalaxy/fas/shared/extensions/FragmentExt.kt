package vn.vngalaxy.fas.shared.extensions

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions

//fun <T : ViewBinding> Fragment.withBinding(bind: (View) -> T, withBinding: (binding: T) -> Unit) {
//    view?.let { view ->
//        val binding = bind(view)
//        withBinding(binding)
//    }
//}

fun Fragment.navigate(directions: NavDirections, navOptions: NavOptions? = null) =
    runCatching {
        val nav = findNavController()
        nav.navigate(directions, navOptions)
    }

fun Fragment.navigateBack() = runCatching {
    findNavController().popBackStack()
}

fun Fragment.navigateAndClearStack(directions: NavDirections) = runCatching {
    val nav = findNavController()

    nav.navigate(directions, navOptions {
        popUpTo(nav.graph.id) {
            inclusive = true
        }
    })
}
