package com.example.myapplication

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityAddTodoBinding
import com.example.myapplication.model.Todo
import com.example.myapplication.viewmodels.AddTodoViewModel
import com.example.myapplication.viewmodels.AddTodoViewModelFactory
import com.example.myapplication.viewmodels.TodoViewModel

import com.example.myapplication.viewmodels.TodoViewModelFactory
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

    private lateinit var todoViewModel: TodoViewModel
    private lateinit var binding: ActivityAddTodoBinding
    private lateinit var todo: Todo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent.extras



        val modelFactory = TodoViewModelFactory(application)

        todoViewModel = ViewModelProvider(this, modelFactory)[TodoViewModel::class.java]


        binding.saveTodoBtn.setOnClickListener { view ->
            val todo = Todo(
                id = null,
                title = binding.todoEditText.text.toString(),
                time = 1000000L,
                isComplete = false
            )
            todoViewModel.saveTodo(todo)
            onBackPressed()
//            createTodo(intent)
//            if (binding.todoEditText.text.toString() != "") {
//               val title =  if (binding.todoEditText.text.toString() == "")  todo.title else binding.todoEditText.text.toString()
//                var datetime: LocalDateTime? = try {
//                    LocalDateTime.of(model.savedYear, model.savedMonth, model.savedDay, model.savedHour, model.savedMinute)
//                } catch (e: DateTimeException) {
//                    null
//                }
//
//
//                if (datetime == null) datetime = LocalDateTime.ofInstant(todo.time?.let {
//                    Instant.ofEpochSecond(
//                        it
//                    )
//                },TimeZone.getDefault().toZoneId())
//
//                GlobalScope.launch(Dispatchers.IO) {
//
//                    model.insert(
//                        Todo(
//                            null, title, false, datetime?.atZone(
//                                ZoneId.systemDefault()
//                            )?.toEpochSecond()
//                        )
//                    )
//
//
//                }
//                onBackPressed()
//            } else {
//
//                Snackbar.make(view, "Похоже на пустое задание", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null)
//                    .show()
//
//            }


        }



    binding.openTimeBtn.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(this, this, year, month, day).show()
        }

    }

    private fun createTodo(intent: Bundle?) {

//        var datetime: LocalDateTime? = try {
//                    LocalDateTime.of(model.savedYear, model.savedMonth, model.savedDay, model.savedHour, model.savedMinute)
//                } catch (e: DateTimeException) {
//                    null
//                }
//        if (intent !=null){
//            todo = Todo(intent.getInt("tId"),intent.getString("title").toString(),intent.getBoolean("isComplete"),intent.getLong("time"))
//        }
//        else{
//
//        }

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
//    model.savedDay = dayOfMonth
//    model.savedYear = year
//    model.savedMonth = month
//
//
//    getDateTimeCalendar()
//    TimePickerDialog(this, this, hour, minute, true).show()
}

override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
//    val formatter = DateTimeFormatter.ofPattern("dd MMM. yyyy г., HH:mm")
//    model.savedHour = p1
//    model.savedMinute = p2
//    val datetime = LocalDateTime.of(model.savedYear, model.savedMonth, model.savedDay, model.savedHour, model.savedMinute)
//    val selectedDatetime = datetime.format(formatter).lowercase()
//    binding.timeTV.text = selectedDatetime


}
}