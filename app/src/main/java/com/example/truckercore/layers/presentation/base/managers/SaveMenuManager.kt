package com.example.truckercore.layers.presentation.base.managers

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import com.example.truckercore.R

class SaveMenuManager(
    private val invalidate: () -> Unit,
    private val onSave: () -> Unit
) {

    private var menuEnabled: Boolean = false

    fun provider() = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.menu_save_edit, menu)
        }

        override fun onPrepareMenu(menu: Menu) {
            super.onPrepareMenu(menu)
            val item = menu.findItem(R.id.action_save)
            item.isEnabled = menuEnabled
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return if (menuItem.itemId == R.id.action_save) {
                onSave()
                true
            } else false
        }

    }

    fun disableMenu() {
        menuEnabled = false
        invalidate()
    }

    fun enableMenu() {
        menuEnabled = true
        invalidate()
    }

}