package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.RecyclerviewTodoItemBinding
import com.example.myapplication.model.ToDo
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class TodoListAdapter (private val onClickListener : OnClickListener) : ListAdapter<ToDo, TodoListAdapter.ToDoViewHolder>(
    TodoComparator()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerviewTodoItemBinding.inflate(inflater, parent, false)


        return ToDoViewHolder(binding)


    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val todo = getItem(position)
        holder.itemView.setOnLongClickListener{
            onClickListener.onClick(todo)
            return@setOnLongClickListener true
        }
        holder.bind(todo)

    }



    class ToDoViewHolder(private val binding : RecyclerviewTodoItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(todo : ToDo){
            binding.todoTextView.text = todo.title

            binding.isCompleteCheckBox.isChecked = todo.isComplete
            if(todo.time != null){
                binding.dateTodoTextView.setTextColor(ContextCompat.getColor(itemView.context ,
                    R.color.timeRedColor
                ))

                binding.dateTodoTextView.text = convertToLocalDateTime(todo.time)
            }
        }
        private fun convertToLocalDateTime (time : Long?) : String{
            val formatter = DateTimeFormatter.ofPattern("dd MMM. yyyy г., HH:mm")
            return LocalDateTime.ofInstant(time?.let { Instant.ofEpochSecond(it) },TimeZone.getDefault().toZoneId()).format(formatter)?.lowercase().toString()
        }

    }



    class TodoComparator : DiffUtil.ItemCallback<ToDo>() {
        override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem.id == newItem.id
        }
    }




    class OnClickListener(val clickListener: (toDo: ToDo) -> Unit) {
        fun onClick(todo: ToDo) = clickListener(todo)
    }



}




