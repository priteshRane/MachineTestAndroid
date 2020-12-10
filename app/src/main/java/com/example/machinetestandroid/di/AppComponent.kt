package com.example.machinetestandroid.di

import android.content.Context
import com.example.machinetestandroid.ui.basic.list.MovieListActivity
import dagger.BindsInstance
import dagger.Component

@Component
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(movieListActivity: MovieListActivity)
}