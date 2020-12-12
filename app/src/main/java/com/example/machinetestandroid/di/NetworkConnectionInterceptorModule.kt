package com.example.machinetestandroid.di

import android.content.Context
import com.example.machinetestandroid.data.network.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@InstallIn(ActivityComponent::class)
@Module
class NetworkConnectionInterceptorModule {

    @Provides
    fun provideNetworkConnectionInterceptor(@ActivityContext context: Context): NetworkConnectionInterceptor = NetworkConnectionInterceptor(context)
}
