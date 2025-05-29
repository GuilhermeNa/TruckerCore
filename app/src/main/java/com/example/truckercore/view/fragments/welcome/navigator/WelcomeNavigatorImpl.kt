package com.example.truckercore.view.fragments.welcome.navigator

class WelcomeNavigatorImpl(private val strategy: WelcomeNavigatorStrategy): WelcomeNavigator {

    override fun nextDirection(): Int {
       return strategy.nextNavigationDirection()
    }

}