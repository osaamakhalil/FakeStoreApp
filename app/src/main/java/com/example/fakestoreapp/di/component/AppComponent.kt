package com.example.fakestoreapp.di.component

import android.content.Context
import com.example.fakestoreapp.MyApplication
import com.example.fakestoreapp.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {

    fun inject(app: MyApplication)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}