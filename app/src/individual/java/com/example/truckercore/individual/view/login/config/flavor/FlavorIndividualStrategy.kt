package com.example.truckercore.individual.view.login.config.flavor

import android.content.Context
import android.content.Intent
import com.example.truckercore.individual.view.login.view.activities.MainActivity
import com.example.truckercore.model.configs.flavor.FlavorStrategy

class FlavorIndividualStrategy : FlavorStrategy {

    override fun getFlavor() =
        Individual()

    override fun enterSystemIntent(context: Context) =
        Intent(context, MainActivity::class.java)

}