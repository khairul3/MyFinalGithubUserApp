package com.khairul.myfinalgithubuserapp.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.khairul.myfinalgithubuserapp.R
import com.khairul.myfinalgithubuserapp.util.Reminder

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var preference: SwitchPreferenceCompat
    private lateinit var alarm: Reminder
    private lateinit var reminder: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = context?.getString(R.string.settings)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey)
        alarm = Reminder()
        reminder = getString(R.string.reminder_key)
        preference = findPreference<SwitchPreferenceCompat>(reminder) as SwitchPreferenceCompat
        initSharedPreference()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == reminder) {
            if (sharedPreferences != null) {
                preference.isChecked = sharedPreferences.getBoolean(reminder, false)
            }
        }
        val state: Boolean =
            PreferenceManager.getDefaultSharedPreferences(context).getBoolean(reminder, false)

        setReminder(state)
    }

    private fun initSharedPreference() {
        val sharedPreferences = preferenceManager.sharedPreferences
        preference.isChecked = sharedPreferences.getBoolean(reminder, false)
    }

    private fun setReminder(state: Boolean) {
        if (state) {
            context?.let {
                alarm.setRepeatingReminder(it)
            }
        } else {
            context?.let {
                alarm.cancelAlarm(it)
            }
        }
    }

}