package com.dedan.rekowisatabali

import android.app.Application
import com.dedan.rekowisatabali.container.AppContainer

class SpkApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = com.dedan.rekowisatabali.container.DefaultAppContainer(this)
    }
}