package com.example.runmaster.activities

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACTIVITY_RECOGNITION
import android.Manifest.permission.CAMERA
import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.runmaster.components.bottom_nav.BottomNavView
import com.example.runmaster.components.header.Header
import com.example.runmaster.models.sessions.User
import com.example.runmaster.screens.HistoryScreen
import com.example.runmaster.screens.HomeScreen
import com.example.runmaster.screens.LoginScreen
import com.example.runmaster.screens.OldSessionScreen
import com.example.runmaster.screens.ProfileScreen
import com.example.runmaster.screens.RegisterScreen
import com.example.runmaster.screens.SessionScreen
import com.example.runmaster.screens.SetProfilePicScreen
import com.example.runmaster.services.Chronometer
import com.example.runmaster.services.LocationService
import com.example.runmaster.ui.theme.RunMasterTheme
import com.example.runmaster.utils.ShowTopBottomBar
import com.example.runmaster.utils.navigation.Screen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.example.runmaster.services.SensorEventListener
import com.example.runmaster.view_models.OldSessionViewModel
import com.example.runmaster.view_models.PositionTracker
import com.example.runmaster.utils.LoginDataStore
import com.example.runmaster.view_models.StepCounter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var sensorManager: SensorManager
    private var magnetometer: Sensor? = null
    private var accelerometer: Sensor? = null
    private var stepSensor: Sensor? = null
    private var linearAccelerometer: Sensor? = null
    private lateinit var positionTracker: PositionTracker


    @SuppressLint("ServiceCast")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        linearAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        positionTracker = PositionTracker(this)

        StepCounter.loadData(this)
        if (stepSensor == null) {
            Log.d("Magnetic Sensor", "Magnetic sensor not available")
        } else {
            Log.d("Magnetic Sensor", "Magnetic sensor available")
        }
        setContent {
            RunMasterTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val loginDataStore = LoginDataStore(LocalContext.current)
                val isLoggedInState = loginDataStore.isLoggedInFlow.collectAsState(initial = false)
                val email = loginDataStore.userLoginFlow.collectAsState(initial = "")
                val password = loginDataStore.userPasswordFlow.collectAsState(initial = "")

                if (isLoggedInState.value && email.value.isNotEmpty() && password.value.isNotEmpty()) {
                    User.instance.setInfoByLabel("Email", email.value)
                    User.instance.setInfoByLabel("Password", password.value)
                    User.instance.updateDatabase { success, message -> null }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RequestPermissions(this)
                    Scaffold(
                        topBar = {
                            if (navBackStackEntry?.let { ShowTopBottomBar.showTopBar(it) } == true) Header(
                                navController
                            )
                        },
                        bottomBar = {
                            if (navBackStackEntry?.let { ShowTopBottomBar.showBottomBar(it) } == true) BottomNavView(
                                navController
                            )
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = if (isLoggedInState.value) Screen.Home.route else Screen.Login.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(Screen.Home.route) {
                                HomeScreen(navController, loginDataStore)
                            }
                            composable(Screen.Register.route) {
                                RegisterScreen(navController)
                            }
                            composable(Screen.Login.route) {
                                LoginScreen(navController)
                            }
                            composable(Screen.OldSession.route) {
                                OldSessionScreen()
                            }
                            composable(Screen.History.route) {
                                HistoryScreen(navController)
                            }
                            composable(Screen.Profile.route) {
                                ProfileScreen(navController, loginDataStore)
                            }
                            composable(Screen.Session.route) {
                                SessionScreen(navController, positionTracker)
                            }
                            composable(Screen.SetProfileScreen.route) {
                                SetProfilePicScreen(navController)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            SensorEventListener,
            magnetometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager.registerListener(
            SensorEventListener,
            accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager.registerListener(
            SensorEventListener,
            stepSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager.registerListener(
            SensorEventListener,
            linearAccelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onStop() {
        super.onStop()
        StepCounter.saveData(this)
        if (!Chronometer.isRunning) {
            sensorManager.unregisterListener(SensorEventListener)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissions(activity: ComponentActivity) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val multiplePermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION,
            CAMERA,
            ACTIVITY_RECOGNITION,
        )
    )
    DisposableEffect(key1 = lifecycleOwner) {

        val observer = LifecycleEventObserver { source, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    multiplePermissions.launchMultiplePermissionRequest()
                }

                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    multiplePermissions.permissions.forEach {
        when (it.permission) {
            CAMERA -> {
                if (it.hasPermission) {
                    Log.d("Permission", " Has not Camera permission")
                }
            }

            ACCESS_FINE_LOCATION -> {
                if (it.hasPermission) {
                    CoroutineScope(Dispatchers.Main).launch {
                        LocationService.getDeviceLastKnownLocation(activity)
                    }
                } else {
                    Log.d("Permission", " Has not fine location  permission")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RunMasterTheme {
        Greeting("Android")
    }
}