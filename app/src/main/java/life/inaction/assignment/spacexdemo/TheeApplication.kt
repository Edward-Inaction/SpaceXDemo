package life.inaction.assignment.spacexdemo

import android.app.Application
import life.inaction.assignment.spacexdemo.di.API_MODULE
import life.inaction.assignment.spacexdemo.di.COMMON_MODULE
import life.inaction.assignment.spacexdemo.di.USER_CASE_MODULE
import life.inaction.assignment.spacexdemo.di.VIEW_MODEL_MODULE
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TheeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TheeApplication)
            modules(listOf(COMMON_MODULE, USER_CASE_MODULE, API_MODULE, VIEW_MODEL_MODULE))
        }
    }
}
