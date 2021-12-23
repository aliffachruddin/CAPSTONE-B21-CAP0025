package com.capstone.foodcy.preference

import android.content.Context
import com.capstone.foodcy.data.entity.Reminder

class ReminderPreference(context: Context) {
    companion object {
        const val PREF_NAME = "reminder_pref"
        private const val REMINDER = "isReminder"
    }

    private val preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setReminder(value: Reminder){
        val editor = preference.edit()
        editor.putBoolean(REMINDER, value.isReminder)
        editor.apply()
    }

    fun getReminder(): Reminder {
        val mReminder = Reminder()
        mReminder.isReminder = preference.getBoolean(REMINDER, false)
        return mReminder
    }
}