package com.capstone.foodcy.ui.setting.setreminder

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.capstone.foodcy.R
import com.capstone.foodcy.data.entity.Reminder
import com.capstone.foodcy.databinding.ActivitySetReminderBinding
import com.capstone.foodcy.preference.ReminderPreference
import com.capstone.foodcy.receiver.AlarmReceiver
import com.capstone.foodcy.service.AlarmService
import java.util.*

class SetReminderActivity : AppCompatActivity(){
    lateinit var binding: ActivitySetReminderBinding
    lateinit var alarmService: AlarmService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        alarmService = AlarmService(this)

        binding.setExact.setOnClickListener {
            setAlarm { alarmService.setExactAlarm(it) }
        }

        binding.setRepetitive.setOnClickListener { setAlarm { alarmService.setRepetitiveAlarm(it) } }
    }

    private fun setAlarm(callback: (Long) -> Unit) {
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)
            DatePickerDialog(
                this@SetReminderActivity,
                0,
                { _, year, month, day ->
                    this.set(Calendar.YEAR, year)
                    this.set(Calendar.MONTH, month)
                    this.set(Calendar.DAY_OF_MONTH, day)
                    TimePickerDialog(
                        this@SetReminderActivity,
                        0,
                        { _, hour, minute ->
                            this.set(Calendar.HOUR_OF_DAY, hour)
                            this.set(Calendar.MINUTE, minute)
                            callback(this.timeInMillis)
                        },
                        this.get(Calendar.HOUR_OF_DAY),
                        this.get(Calendar.MINUTE),
                        false
                    ).show()
                },
                this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}
