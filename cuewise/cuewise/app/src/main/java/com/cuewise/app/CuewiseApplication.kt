package com.cuewise.app

import android.app.Application

class CuewiseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RevenueCatManager.configure(this)
    }
}
