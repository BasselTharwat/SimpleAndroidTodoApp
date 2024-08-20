package com.example.todolist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.TodoItemBinding

class TodoAdapter(private val todos: MutableList<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {


    class TodoViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root)

    private fun toggleStrikeThrough(todoItem : TextView, isChecked: Boolean){
        if(isChecked){
            //we want to strike through the finished items
            //what is paintFlags?
            todoItem.paintFlags = todoItem.paintFlags or STRIKE_THRU_TEXT_FLAG
        }else{
            todoItem.paintFlags = todoItem.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    fun addTodo(todo: Todo){
        todos.add(todo)
        //we need to notify our adapter that we've inserted an item
        notifyItemInserted(todos.size - 1) //we need to specify the position of the item
    }

    fun deleteDoneTodos(){
        todos.removeAll { todo -> todo.isChecked }
        notifyDataSetChanged()
    }

    //these functions must be implemented
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
            //layout inflater parses xml to kotlin
            val binding = TodoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)

            return TodoViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        //this binds the data to the views
        val currentTodo = todos[position]
        holder.binding.apply {
            todoItem.text = currentTodo.title
            checkBox.isChecked = currentTodo.isChecked
            toggleStrikeThrough(todoItem, currentTodo.isChecked)
            //we want to apply the toggle function if a change occurred
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(todoItem, isChecked)
                currentTodo.isChecked = !currentTodo.isChecked
            }
            //the underscore means that the first parameter is not needed
            //the second parameter is a lambda function
        }
    }

}