package com.dhemitus.bookshelf

import android.app.Application
import com.dhemitus.bookshelf.di.initKoin
import org.koin.android.ext.koin.androidContext

class BookApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BookApplication)
        }
    }
}