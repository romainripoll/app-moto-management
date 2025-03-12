package com.romainripoll.motogpmanager

import android.app.Application
import androidx.room.Room
import com.romainripoll.motogpmanager.data.local.GameDatabase

class MotoGpManagerApp : Application() {

    companion object {
        lateinit var instance: MotoGpManagerApp
            private set
    }

    // Database instance
    val database: GameDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            GameDatabase::class.java,
            "motogp_manager_db"
        ).build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
