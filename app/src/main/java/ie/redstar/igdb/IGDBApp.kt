package ie.redstar.igdb

import android.app.Application
import ie.redstar.igdb.data.dataModule
import ie.redstar.igdb.ui.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class IGDBApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@IGDBApp)

            modules(
                dataModule,
                uiModule
            )
        }
    }
}