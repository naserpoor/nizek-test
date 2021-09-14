package ir.alireza.naserpour.nizektest

import android.app.Application
import ir.alireza.naserpour.nizektest.di.constModules
import ir.alireza.naserpour.nizektest.di.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.context.KoinContext
import org.koin.core.context.startKoin

class NizekApplication : Application() {

    lateinit var koin: Koin

    override fun onCreate() {
        super.onCreate()


        koin = startKoin {
            androidContext(this@NizekApplication)
            modules(module,constModules)
        }.koin
    }
}