package com.example.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.ToDo
import com.example.todolist.data.ToDoDatabase
import kotlinx.coroutines.launch

class ToDoViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = ToDoDatabase.getDatabase(app).toDoDao()
    val allTodos = dao.getAll()

    fun addTodo(toDo: ToDo) = viewModelScope.launch {
        dao.insert(toDo)
    }

    fun deleteTodo(toDo: ToDo) = viewModelScope.launch {
        dao.delete(toDo)
    }
}
