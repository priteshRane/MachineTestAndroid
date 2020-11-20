package com.example.machinetestandroid

import android.app.Application
import com.example.machinetestandroid.di.AppComponent
import com.example.machinetestandroid.di.DaggerAppComponent

open class MyApplication: Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}