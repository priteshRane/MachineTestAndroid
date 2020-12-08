package com.example.machinetestandroid.di

import android.content.Context
import com.example.machinetestandroid.ui.detail.MovieDetailFragment
import com.example.machinetestandroid.ui.list.MovieListFragment
import dagger.BindsInstance
import dagger.Component

@Component
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(listFragment: MovieListFragment)
    fun inject(detailFragment: MovieDetailFragment)
}