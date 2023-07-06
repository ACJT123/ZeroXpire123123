package my.edu.tarc.zeroxpire

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.impl.Observable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import my.edu.tarc.zeroxpire.databinding.ActivityMainBinding
import my.edu.tarc.zeroxpire.view.ScannerFragment
import my.edu.tarc.zeroxpire.viewmodel.IngredientViewModel

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private lateinit var auth: FirebaseAuth

    private lateinit var networkConnection: NetworkConnection
    private lateinit var networkConnectionObserver: Observable.Observer<Boolean>

    private lateinit var ingredientViewModel: IngredientViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Internet connection
        //TODO: cannot be done currently due to the lead of navigation state lost


        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()


        //initialize ViewModel
        ingredientViewModel = ViewModelProvider(this).get(IngredientViewModel::class.java)


        // Navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        // Find reference to bottom navigation view
        val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        // Hook your navigation controller to bottom navigation view
        navView.setupWithNavController(navController)

        navView.background = null
        navView.menu.getItem(2).isEnabled = false

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.ingredientFragment -> {
                    enableBtmNav()
                    binding.fab.setImageResource(R.drawable.baseline_qr_code_scanner_24)
                    binding.fab.setOnClickListener {
                        navController.navigate(R.id.action_ingredientFragment_to_scannerFragment)
                        disableBtmNav()
                        requestCameraAndStartScanner()
                    }
                }
                R.id.goalFragment -> {
                    enableBtmNav()
                    binding.fab.setImageResource(R.drawable.baseline_add_24)
                    binding.fab.setOnClickListener{
                        navController.navigate(R.id.action_goalFragment_to_createGoalFragment)
                        disableBtmNav()
                    }
                }
                R.id.recipeFragment -> {
                    enableBtmNav()
                    binding.fab.setImageResource(R.drawable.baseline_book_24)
                }
                R.id.profileFragment -> {
                    if(auth.currentUser == null){
                        Toast.makeText(this, "No user", Toast.LENGTH_SHORT).show()
                        disableBtmNav()
                        navController.navigate(R.id.loginFragment)
                        navController.clearBackStack(R.id.loginFragment)
                    }
//                    else{
//                        navController.navigate(R.id.)
//                        Toast.makeText(this, "User", Toast.LENGTH_SHORT).show()
//                    }
//                    disableBtmNav()
//                    binding.bottomAppBar.visibility = View.VISIBLE
//                    binding.bottomAppBar.fabCradleMargin = -50f
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "On resuming", Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "On restarting", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "On starting", Toast.LENGTH_SHORT).show()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            Toast.makeText(this, "Already Signed In", Toast.LENGTH_SHORT).show()
//        }
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "On pausing", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "On destroying", Toast.LENGTH_SHORT).show()
    }

    private val cameraPermission = android.Manifest.permission.CAMERA
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startScanner()
        }
    }

    private fun requestCameraAndStartScanner(){
        if(isPermissionGranted(cameraPermission)){
            startScanner()
        }
        else{
            requestCameraPermission()
        }
    }

    private fun requestCameraPermission() {
        when{
            shouldShowRequestPermissionRationale(cameraPermission) ->{
                cameraPermissionRequest{
                    openPermissionSetting()
                }
            }
            else -> {
                requestPermissionLauncher.launch(cameraPermission)
            }
        }
    }

    private fun startScanner(){
        ScannerFragment.startScanner(this){
        }
    }

    private fun disableBtmNav() {
        binding.bottomAppBar.visibility = View.INVISIBLE
        binding.fab.visibility = View.INVISIBLE
    }

    private fun enableBtmNav() {
        binding.bottomAppBar.visibility = View.VISIBLE
        binding.fab.visibility = View.VISIBLE
        binding.bottomAppBar.fabCradleMargin = 20f
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}