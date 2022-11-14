package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityAddTodoBinding
import com.example.myapplication.model.Todo
import com.example.myapplication.utils.Constants
import com.example.myapplication.viewmodels.TodoViewModel
import com.example.myapplication.viewmodels.TodoViewModelFactory


class AddTodoActivity : AppCompatActivity() {


    private lateinit var todoViewModel: TodoViewModel
    private lateinit var binding: ActivityAddTodoBinding
    private var todo: Todo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent != null) {
            val todo = intent.getParcelableExtra<Todo>(Todo::class.java.simpleName)
            this.todo = todo
            if (todo != null) {
                binding.todoTitleText.setText(todo.title)
                binding.todoDescriptionText.setText(todo.description)

            }
        }

        val modelFactory = TodoViewModelFactory(application)

        todoViewModel = ViewModelProvider(this, modelFactory)[TodoViewModel::class.java]


        binding.saveTodoBtn.setOnClickListener {

            if (validateInfo()) {
                if(Constants.EDIT_TODO == 1){
                    val id = if (todo != null) todo?.id else null
                    val isComplete = todo?.isComplete ?: false
                    val todo = Todo(
                        id = id,
                        title = binding.todoTitleText.text.toString(),

                        description = binding.todoDescriptionText.text.toString(),
                        isComplete = isComplete
                    )
                    Log.i("123","$todo")
                    todoViewModel.updateTodo(todo)
                    Constants.EDIT_TODO = 0
                    finish()
                }
                else if(Constants.ADD_TODO == 1){
                    val todo = Todo(
                        id = null,
                        title = binding.todoTitleText.text.toString(),

                        description = binding.todoDescriptionText.text.toString(),
                        isComplete = false
                    )
                    todoViewModel.saveTodo(todo)
                    Constants.ADD_TODO = 0
                    finish()
                }


            }


        }
    }



    private fun validateInfo(): Boolean {
        if (binding.todoDescriptionText.text.toString().isEmpty()) {
            binding.todoTextError.error = "Задание пустое!"
            binding.todoTextError.requestFocus()
            return false
        }


        return true

    }
}


