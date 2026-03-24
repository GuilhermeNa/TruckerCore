package com.example.truckercore.business_admin.layers.presentation.main.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.truckercore.R
import com.example.truckercore.business_admin.layers.presentation.main.activity.view_model.MainViewModel
import com.example.truckercore.databinding.ActivityMainBinding
import com.example.truckercore.layers.presentation.base.contracts.BaseNavigator
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
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
class MainActivity : AppCompatActivity(), BaseNavigator {

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

    private val res = MainResources()

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
        setNavDestinationListener()

        // State Managers
        observeSessionState()

    }

    private fun observeSessionState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sessionFlow.collect { session ->
                    session.exitApp(
                        onError = { navigateToErrorActivity(this@MainActivity) },
                        onLogout = { finish() }
                    )
                }
            }
        }
    }

    /**
     * Configures the Drawer with the NavController.
     *
     * Responsibilities:
     * - Defines top-level destinations.
     * - Connects the Toolbar with the Navigation Component.
     * - Binds the Drawer menu with the NavController.
     */
    private fun setupDrawerNavController() {
        drawerConfiguration = AppBarConfiguration(res.topLevelDestination, binding.drawerLayout)
        setupActionBarWithNavController(navController, drawerConfiguration)
        binding.drawerView.setupWithNavController(navController)
    }

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
     */
    private fun logout() {
        viewModel.logout()
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
     * Registers a NavController destination change listener.
     *
     * Responsibilities:
     * - Observes navigation destination changes.
     * - Closes the Drawer when navigating to a top-level destination.
     * - Controls Toolbar menu visibility based on the current destination.
     */
    private fun setNavDestinationListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->

            // Close the drawer when reaching a top-level destination
            // to preserve expected Material navigation behavior.
            if (res.isTopLevelDestination(destination)) {
                binding.drawerLayout.closeDrawers()
            }

            // Disable Toolbar menu items when navigating to
            // destinations triggered from the options menu.
            if (res.isMenuDestination(destination)) {
                viewModel.disableMenu()
            } else viewModel.enableMenu()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(drawerConfiguration)
                || super.onSupportNavigateUp()
    }

    // ---------------------------------------------------------------------------------------------
    // Options Menu Configuration (Toolbar)
    // ---------------------------------------------------------------------------------------------
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.act_main_appbar_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        setMenuStateManager(menu)
        return super.onPrepareOptionsMenu(menu)
    }

    /**
     * Applies visibility changes to all menu items.
     *
     * Responsibilities:
     * - Iterates through all menu items.
     * - Toggles their visibility based on the provided state.
     *
     * Extension Function:
     * - Improves readability and encapsulates menu manipulation logic.
     */
    private fun setMenuStateManager(menu: Menu?) {
        if (menu == null) return

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.menuState.collect { isEnabled ->
                    menu.forEach { it.isVisible = isEnabled }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_message -> {
                navController.navigate(R.id.action_global_nav_messages)
            }

            R.id.action_profile -> {
                navController.navigate(R.id.action_global_nav_profile)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}