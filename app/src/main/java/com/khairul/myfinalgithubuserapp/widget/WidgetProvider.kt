package com.khairul.myfinalgithubuserapp.widget

import android.R
import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.khairul.myfinalgithubuserapp.database.UserDao
import com.khairul.myfinalgithubuserapp.database.UserDatabase
import com.khairul.myfinalgithubuserapp.model.GithubUserModel

class WidgetProvider(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private lateinit var model: List<GithubUserModel>
    private lateinit var data: UserDao

    override fun onCreate() {
        data = UserDatabase.getDatabase(context).userDao()
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = model[position].id.toLong()

    override fun onDataSetChanged() {
        model = data.getWidgetList()
    }

    override fun hasStableIds(): Boolean = true

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.simple_list_item_1).also {
            it.setTextViewText(R.id.text1, model[position].login)
        }
        return remoteViews
    }

    override fun getCount(): Int = model.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
    }
}