package ru.irinavb.sensors

import android.hardware.Sensor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import org.koin.android.ext.android.get
import org.koin.core.context.GlobalContext.get
import ru.irinavb.sensors.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setContentView(view)

        val test = get<Test>()
        test.getSensorManagerFromTest().getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}