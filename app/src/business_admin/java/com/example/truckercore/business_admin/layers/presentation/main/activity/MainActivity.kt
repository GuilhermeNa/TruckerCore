package com.example.truckercore.business_admin.layers.presentation.main.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.truckercore.R
import com.example.truckercore.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * MainActivity
 *
 * This is the main entry point of the application after authentication.
 *
 * Responsibilities:
 * - Hosts the Navigation Component's NavHostFragment.
 * - Configures and manages the Navigation Drawer.
 * - Integrates the Drawer with the NavController.
 * - Handles top-level navigation behavior.
 * - Manages logout flow.
 * - Binds user information to the drawer header.
 *
 * Architecture:
 * - Uses ViewBinding for layout access.
 * - Uses Koin (or DI framework) for ViewModel injection.
 * - Uses Android Navigation Component for fragment navigation.
 */
class MainActivity : AppCompatActivity() {

    /**
     * AppBarConfiguration defines the top-level destinations.
     * These destinations will display the drawer icon instead of the Up button.
     */
    private lateinit var drawerConfiguration: AppBarConfiguration

    /**
     * ViewBinding reference for activity layout.
     */
    private lateinit var binding: ActivityMainBinding

    /**
     * Main ViewModel instance injected via dependency injection.
     * Responsible for user session and UI-related data.
     */
    private val viewModel: MainViewModel by viewModel()

    /**
     * Lazy reference to the NavController associated with the NavHostFragment.
     */
    private val navController: NavController by lazy {
        findNavController(R.id.act_main_nav_host)
    }

    /**
     * Defines all top-level destinations of the Drawer.
     * These destinations will:
     * - Show the hamburger icon instead of the Up arrow
     * - Close the drawer when selected
     */
    private val drawerTopLevelDestination = setOf(
        R.id.nav_home,
        R.id.nav_business,
        R.id.nav_employees,
        R.id.nav_fleet,
        R.id.nav_fines,
        R.id.nav_settings
    )

    //----------------------------------------------------------------------------------------------
    // On Create
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        setSupportActionBar(binding.appBarMain.toolbar)

        //Drawer Config
        setupDrawerNavController()
        setLogoutCLickListener()
        bindDrawerHeader()
    }

    /**
     * Handles Up navigation behavior.
     *
     * Delegates navigation handling to the NavController using
     * the AppBarConfiguration to determine whether to:
     * - Open the drawer (for top-level destinations)
     * - Navigate up in the back stack
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(drawerConfiguration)
                || super.onSupportNavigateUp()
    }

    // ---------------------------------------------------------------------------------------------
    // Drawer Configuration
    // ---------------------------------------------------------------------------------------------

    /**
     * Configures the Drawer with the NavController.
     *
     * Responsibilities:
     * - Defines top-level destinations.
     * - Connects the Toolbar with the Navigation Component.
     * - Binds the Drawer menu with the NavController.
     * - Closes the drawer when navigating to a top-level destination.
     */
    private fun setupDrawerNavController() {

        drawerConfiguration =
            AppBarConfiguration(drawerTopLevelDestination, binding.drawerLayout)

        setupActionBarWithNavController(navController, drawerConfiguration)
        binding.drawerView.setupWithNavController(navController)

        // Manually close the drawer whenever navigation reaches
        // a top-level destination to restore the expected standard behavior.
        // Since the Drawer layout is composed of two separate NavigationViews
        // and we loosed the automatic drawer-closing behavior.
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (isTopLevel(destination)) {
                binding.drawerLayout.closeDrawers()
            }
        }
    }

    /**
     * Checks whether a given destination is a top-level destination.
     *
     * @param destination Current navigation destination
     * @return true if destination is top-level
     */
    private fun isTopLevel(destination: NavDestination): Boolean =
        drawerTopLevelDestination.contains(destination.id)

    /**
     * Configures the logout menu item behavior.
     *
     * Since logout is not a navigation destination,
     * it is handled manually instead of through the NavController.
     */
    private fun setLogoutCLickListener() {
        binding.logoutView.menu
            .findItem(R.id.action_logout)
            .setOnMenuItemClickListener {
                showLogoutDialog()
                binding.drawerLayout.closeDrawers()
                true
            }
    }

    /**
     * Binds user information to the Drawer header.
     *
     * Retrieves the header layout and updates:
     * - Profile image
     * - User name
     * - User email
     */
    private fun bindDrawerHeader() {
        val headerView = binding.drawerView.getHeaderView(0)

        val imageView = headerView.findViewById<ImageView>(R.id.drawer_image)
        val mainTextView = headerView.findViewById<TextView>(R.id.drawer_header_text)
        val helperTextView = headerView.findViewById<TextView>(R.id.drawer_email_text)

        imageView.setImageResource(R.drawable.icon_person)
        mainTextView.text = viewModel.name.value
        helperTextView.text = viewModel.email.value
    }

    /**
     * Displays a confirmation dialog before logging the user out.
     */
    private fun showLogoutDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Saindo")
            .setMessage("Você realmente deseja sair do App?")
            .setPositiveButton("Sim") { _, _ -> logout() }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    /**
     * Executes the logout process.
     *
     * - Clears user session via ViewModel.
     * - Finishes the activity.
     */
    private fun logout() {
        viewModel.logout()
        finish()
    }

    // ---------------------------------------------------------------------------------------------
    // Options Menu Configuration (Toolbar)
    // ---------------------------------------------------------------------------------------------

    /**
     * Inflates the toolbar menu.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.act_main_appbar_main, menu)
        return true
    }

    /**
     * Handles toolbar menu item selection.
     *
     * Delegates navigation handling to the NavController.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_messages -> {
                checkEntry(navController.currentBackStackEntry)
                navController.navigate(R.id.nav_messages)
            }

            R.id.nav_profile -> {
                navController.navigate(R.id.nav_profile)
            }
        }


        return super.onOptionsItemSelected(item)

        /*  return item.onNavDestinationSelected(navController)
                  || super.onOptionsItemSelected(item)*/
    }

    private fun checkEntry(entry: NavBackStackEntry?) {
        if (entry == null) throw NullPointerException()
        if (entry.destination.id == R.id.nav_messages) {
            navController
        }
    }

}