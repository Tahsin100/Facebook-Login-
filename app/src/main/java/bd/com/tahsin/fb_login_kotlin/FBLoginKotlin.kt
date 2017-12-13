package bd.com.tahsin.fb_login_kotlin

import android.app.Application
import com.facebook.appevents.AppEventsLogger

/**
 * Created by Tahsin on 12-Dec-17.
 */


class FBLoginKotlin : Application(){

    override fun onCreate() {
        super.onCreate()

        AppEventsLogger.activateApp(this)
    }
}