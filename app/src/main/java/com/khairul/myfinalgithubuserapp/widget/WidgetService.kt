package com.khairul.myfinalgithubuserapp.widget

import android.content.Intent
import android.widget.RemoteViewsService

class WidgetService : RemoteViewsService() {
    override fun onGetViewFactory(p0: Intent?): RemoteViewsFactory {
        return WidgetProvider(this.applicationContext)
    }
}