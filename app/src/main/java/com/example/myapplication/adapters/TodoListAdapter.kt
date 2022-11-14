package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.RecyclerviewTodoItemBinding
import com.example.myapplication.model.Todo


class TodoListAdapter(onClickListener: ITodoListener) : ListAdapter<Todo, TodoListAdapter.ToDoViewHolder>(
    ToDoViewHolder.TodoComparator()) , Filterable{

    private val listener: ITodoListener = onClickListener
    private var todoList: List<Todo> =  arrayListOf()

    private var filteredTodoList: List<Todo> = arrayListOf()


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


        submitList(todo)

    }



    class ToDoViewHolder(private val binding : RecyclerviewTodoItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(todo: Todo, listener: ITodoListener){
            binding.todoTitleTextView.text = todo.title
            binding.todoDescriptionTextView.text = todo.description


            binding.root.setOnLongClickListener{
                listener.onLongClickItem(todo)
                return@setOnLongClickListener true

            }







        
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
        fun onLongClickItem(item: Todo)


    }


    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {

                val searchtext = p0.toString()
                filteredTodoList = if(searchtext.isEmpty()){

                     todoList
                }
                else{
                    val filteredList = arrayListOf<Todo>()
                    for(item in todoList){
                        if(!item.title.isNullOrEmpty() &&
                            item.title!!.lowercase().trim().contains(searchtext.lowercase().trim()) ||
                            (!item.description.isNullOrEmpty() &&
                                    item.description!!.lowercase().trim().contains(searchtext.lowercase().trim()))){
                            filteredList.add(item)
                        }

                    }
                    filteredList
                }


                val filterResults = FilterResults()
                filterResults.values = filteredTodoList

                return filterResults

            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredTodoList = p1?.values as List<Todo>
                //set list to be displayed
                submitList(filteredTodoList)
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






