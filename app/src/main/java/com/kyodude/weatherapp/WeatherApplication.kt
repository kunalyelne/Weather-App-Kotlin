package com.kyodude.weatherapp

import android.app.Activity
import android.app.Application
import android.content.pm.ActivityInfo
import android.os.Bundle
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(object: ActivityLifecycleCallbacks {
            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                p0.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            }

            override fun onActivityStarted(p0: Activity) {
            }

            override fun onActivityResumed(p0: Activity) {
            }

            override fun onActivityPaused(p0: Activity) {
            }

            override fun onActivityStopped(p0: Activity) {
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }

            override fun onActivityDestroyed(p0: Activity) {
            }

        })
    }
}