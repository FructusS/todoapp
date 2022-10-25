package com.example.myapplication.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.CompletedActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.RecyclerviewTodoItemBinding
import com.example.myapplication.model.Todo
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class TodoListAdapter(onClickListener: ITodoListener) : ListAdapter<Todo, TodoListAdapter.ToDoViewHolder>(
    ToDoViewHolder.TodoComparator()) , Filterable{

    private val listener: ITodoListener = onClickListener
    private var todoList: List<Todo> =  arrayListOf()
    private var todoListfalse: List<Todo> =  arrayListOf()
    private var filteredtodoList: List<Todo> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerviewTodoItemBinding.inflate(inflater, parent, false)


        return ToDoViewHolder(binding)


    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val todo = getItem(position)

        holder.bind(todo,listener)

    }
    // set currentlist in todoList  and set list to be displayed.
    fun setList(todo : List<Todo>){

        this.todoList = todo

        notifyDataSetChanged()
        submitList(todo)

    }



    class ToDoViewHolder(private val binding : RecyclerviewTodoItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(todo: Todo, listener: ITodoListener){
            binding.todoTextView.text = todo.title


            if(todo.time != null){
                binding.dateTodoTextView.setTextColor(ContextCompat.getColor(itemView.context ,
                    R.color.timeRedColor
                ))

                binding.dateTodoTextView.text = convertToLocalDateTime(todo.time)
            }



            binding.root.setOnLongClickListener{
                listener.onLongClickItem(todo , position)
                return@setOnLongClickListener true

            }







        
    }

        private fun convertToLocalDateTime (time : Long?) : String{
            val formatter = DateTimeFormatter.ofPattern("dd MMM. yyyy г., HH:mm")
            return LocalDateTime.ofInstant(time?.let { Instant.ofEpochSecond(it) },TimeZone.getDefault().toZoneId()).format(formatter)?.lowercase().toString()
        }


    class TodoComparator : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }
    }







}

    interface ITodoListener {
        fun onItemComplete(item: Todo, position: Int)
        fun onItemNotComplete(item: Todo, position: Int)
        fun onLongClickItem(item: Todo,position: Int)

    }


    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {

                val searchtext = p0.toString();
                //if searchtext is null that filteredtodoList equals todoList else create new list wherein add  items which we find
                filteredtodoList = if(searchtext.isEmpty()){

                     todoList
                }
                else{
                    val filteredList = arrayListOf<Todo>()
                    for(item in todoList){
                        if(item.title.lowercase().trim().contains(searchtext.lowercase().trim())){
                            filteredList.add(item)
                        }
                    }
                    filteredList
                }

                Log.i("123",todoList.size.toString())
                Log.i("123",filteredtodoList.size.toString())
                val filterResults = FilterResults()
                filterResults.values = filteredtodoList

                return filterResults

            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredtodoList = p1?.values as List<Todo>
                //set list to be displayed
                submitList(filteredtodoList)
            }

        }
    }

    fun onItemComplete(position: Int) {
        listener.onItemComplete(todoList[position], position)
    }
    fun onItemNotComplete(position: Int) {
        listener.onItemNotComplete(todoList[position], position)
    }


}






