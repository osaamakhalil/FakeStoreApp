package com.example.fakestoreapp.di.component

import android.content.Context
import com.example.fakestoreapp.MainActivity
import com.example.fakestoreapp.MyApplication
import com.example.fakestoreapp.di.module.AppModule
import com.example.fakestoreapp.di.module.DataModule
import com.example.fakestoreapp.di.module.RemoteModule
import com.example.fakestoreapp.di.module.ViewModelsModule
import com.example.fakestoreapp.presentation.products.view.ProductsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RemoteModule::class,
        DataModule::class,
        ViewModelsModule::class
    ]
)
interface AppComponent {

    fun inject(app: MyApplication)

    fun inject(activity: MainActivity)

    fun inject(fragment: ProductsFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}