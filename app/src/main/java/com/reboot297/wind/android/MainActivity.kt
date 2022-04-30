package com.reboot297.wind.android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reboot297.wind.android.screens.*
import com.reboot297.wind.android.ui.theme.WindTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WindTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }

        if (intent?.type == "application/json") {
            viewModel.load(intent?.data!!)
        }

        viewModel.urlData.observe(this) {
            it?.let {
                openUrl(it)
            }
            viewModel.urlData.postValue(null)
        }
    }

    private fun openUrl(url: String) {
        startActivity(
            Intent(Intent.ACTION_VIEW)
                .setData(Uri.parse(url))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WindTheme {
        MainScreen()
    }
}

@Composable
fun MainScreen() {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(scope = scope, scaffoldState = scaffoldState) },
        drawerBackgroundColor = colorResource(id = R.color.colorPrimary),
        // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
        drawerContent = {
            Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
        },
    ) {
        Navigation(navController = navController)
    }
}

sealed class NavDrawerItem(var route: String, var icon: Int, var title: String) {
    object Home : NavDrawerItem("home", R.drawable.ic_launcher_foreground, "Home")
    object Settings : NavDrawerItem("settings", R.drawable.ic_launcher_foreground, "Settings")
    object About : NavDrawerItem("about", R.drawable.ic_launcher_foreground, "About")
}

@Composable
fun Navigation(navController: NavHostController) {
    val viewModel = hiltViewModel<MainViewModel>()
    NavHost(navController, startDestination = NavDrawerItem.Home.route) {
        composable(NavDrawerItem.Home.route) {
            HomeScreen(viewModel)
        }
        composable(NavDrawerItem.Settings.route) {
            SettingsScreen()
        }
        composable(NavDrawerItem.About.route) {
            AboutScreen()
        }
    }
}