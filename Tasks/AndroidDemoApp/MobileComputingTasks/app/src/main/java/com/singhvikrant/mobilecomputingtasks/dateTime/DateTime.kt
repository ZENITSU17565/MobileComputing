package com.singhvikrant.mobilecomputingtasks.dateTime

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.*
import com.singhvikrant.mobilecomputingtasks.R
import com.singhvikrant.mobilecomputingtasks.ui.NoteActivity
import java.text.DecimalFormat
import java.util.*

class DateTime(private val noteView: NoteActivity): DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private var format = DecimalFormat("00")


    private var day = 0
    private var month = 0
    private var year =  0
    private var hour12 = 0
    private var hour24 = 0
    private var minute = 0
    private var amPm = ""


    private var saveDay = 0
    private var saveMonth = 0
    private var saveYear =  0
    private var saveHour12 = 0
    private var saveHour24 = 0
    private var saveMinute = 0
    private var saveAmPm = ""

    init {
        dateTime()
    }


    private fun getDateTimeCalender() {
        val calendar: Calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)+1
        year = calendar.get(Calendar.YEAR)
        hour24 = calendar.get(Calendar.HOUR_OF_DAY)
        hour12 = calendar.get(Calendar.HOUR_OF_DAY)
        amPm = if (hour12 >= 12){
            "PM"
        }else{
            "AM"
        }
        hour12 %= 12
        if (hour12 == 0){
            hour12 = 12
        }

        minute = calendar.get(Calendar.MINUTE)


        val dateTime: TextView = noteView.findViewById(R.id.date_time)
        dateTime.text = "${format.format(day)}/${format.format(month)}/${format.format(year)} , ${format.format(hour12)}:${format.format(minute)} $amPm"
        println("Curr Date : ${format.format(day)}/${format.format(month)}/${format.format(year)} , ${format.format(hour12)}:${format.format(minute)} $amPm")

        month -= 1

    }

    private fun dateTime() {
        getDateTimeCalender()
        val dateTime:TextView = noteView.findViewById(R.id.date_time)
        dateTime.setOnClickListener {

            val dp = DatePickerDialog(noteView, this, year, month, day)
            dp.datePicker.minDate = System.currentTimeMillis()
            dp.show()

        }

        val date: ImageView = noteView.findViewById(R.id.select_date_time)
        date.setOnClickListener {

            val dp = DatePickerDialog(noteView, this, year, month, day)
            dp.datePicker.minDate = System.currentTimeMillis()
            dp.show()

        }

    }



    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        saveDay = day
        saveMonth = month+1
        saveYear = year


        getDateTimeCalender()
        TimePickerDialog(view?.context, this, hour24, minute, false).show()

    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        saveAmPm = if (hour >= 12){
            "PM"
        }else{
            "AM"
        }
        saveHour24 = hour
        saveHour12 = hour%12
        if (saveHour12 == 0){
            saveHour12 = 12
        }
        saveMinute = minute


        val dateTime: TextView = noteView.findViewById(R.id.date_time)
        dateTime.text = "${format.format(saveDay)}/${format.format(saveMonth)}/${format.format(saveYear)} , ${format.format(saveHour12)}:${format.format(saveMinute)} $saveAmPm"
        println("Saved Date : ${format.format(saveDay)}/${format.format(saveMonth)}/${format.format(saveYear)} , ${format.format(saveHour12)}:${format.format(saveMinute)} $saveAmPm")

        updateDateTime()
    }

    private fun updateDateTime(){
        day = saveDay
        month = saveMonth -1
        year = saveYear
        hour12 = saveHour12
        hour24 = saveHour24
        minute = saveMinute
        amPm = saveAmPm
    }

    fun getDateTime(): String {
        return "${format.format(day)}/${format.format(month+1)}/${format.format(year)} , ${format.format(hour24)}:${format.format(minute)}"
    }
}