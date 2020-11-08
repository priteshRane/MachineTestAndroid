package com.example.machinetestandroid.di

import android.content.Context
import com.example.machinetestandroid.ui.detail.DetailFragment
import com.example.machinetestandroid.ui.list.ListFragment
import dagger.BindsInstance
import dagger.Component

@Component
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(listFragment: ListFragment)
}