package com.example.myapplication

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.model.ToDo
import com.example.myapplication.viewmodels.ToDoViewModel
import com.example.myapplication.viewmodels.ToDoViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.DateTimeException
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class AddTodoActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var savedDay = 0
    private var savedMonth = 0
    private var savedYear = 0
    private var savedHour = 0
    private var savedMinute = 0

    private lateinit var model: ToDoViewModel


    private lateinit var openTimeBtn: ImageButton
    private lateinit var toDo: ToDo
    private lateinit var saveTodoBtn: Button
    private lateinit var timeTV: TextView
    private lateinit var todoEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)
        val intent = intent.extras
        if(intent != null){
            toDo = intent.getSerializable(ToDo::class.java.simpleName) as ToDo

        }

        val modelfactory = ToDoViewModelFactory(application)

        model = ViewModelProvider(this, modelfactory)[ToDoViewModel::class.java]






        openTimeBtn = findViewById(R.id.openTimeBtn)
        saveTodoBtn = findViewById(R.id.saveTodoBtn)
        timeTV = findViewById(R.id.timeTV)
        todoEditText = findViewById(R.id.todoEditText)


        saveTodoBtn.setOnClickListener { view ->


            if (todoEditText.text.toString() != "") {
               val title =  if (todoEditText.text.toString() == "")  toDo.title else todoEditText.text.toString()
                var datetime: LocalDateTime? = try {
                    LocalDateTime.of(model.savedYear, model.savedMonth, model.savedDay, model.savedHour, model.savedMinute)
                } catch (e: DateTimeException) {
                    null
                }


                if (datetime == null) datetime = LocalDateTime.ofInstant(toDo.time?.let {
                    Instant.ofEpochSecond(
                        it
                    )
                },TimeZone.getDefault().toZoneId())

                GlobalScope.launch(Dispatchers.IO) {

                    model.insert(
                        ToDo(
                            null, title, false, datetime?.atZone(
                                ZoneId.systemDefault()
                            )?.toEpochSecond()
                        )
                    )


                }
                onBackPressed()
            } else {

                Snackbar.make(view, "Похоже на пустое задание", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()

            }


        }



        openTimeBtn.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(this, this, year, month, day).show()
        }

    }



private fun getDateTimeCalendar() {
    val cal: Calendar = Calendar.getInstance()

    day = cal.get(Calendar.DAY_OF_MONTH)
    month = cal.get(Calendar.MONTH) + 1
    year = cal.get(Calendar.YEAR)
    hour = cal.get(Calendar.HOUR)
    minute = cal.get(Calendar.MINUTE)


}

override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
    model.savedDay = dayOfMonth
    model.savedYear = year
    model.savedMonth = month


    getDateTimeCalendar()
    TimePickerDialog(this, this, hour, minute, true).show()
}

override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
    val formatter = DateTimeFormatter.ofPattern("dd MMM. yyyy г., HH:mm")
    model.savedHour = p1
    model.savedMinute = p2
    val datetime = LocalDateTime.of(model.savedYear, model.savedMonth, model.savedDay, model.savedHour, model.savedMinute)
    val selectedDatetime = datetime.format(formatter).lowercase()
    timeTV.text = selectedDatetime


}
}