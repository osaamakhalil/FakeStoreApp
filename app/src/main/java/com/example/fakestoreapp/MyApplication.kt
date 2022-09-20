package com.example.fakestoreapp

import android.app.Application
import com.example.fakestoreapp.di.component.AppComponent
import com.example.fakestoreapp.di.component.DaggerAppComponent
import timber.log.Timber

class MyApplication : Application() {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        provideAppComponent().inject(this)
        Timber.plant(Timber.DebugTree())
    }

    fun provideAppComponent() = appComponent
}