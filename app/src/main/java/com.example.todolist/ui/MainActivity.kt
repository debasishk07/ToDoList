package com.example.todolist.ui

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.todolist.R
import com.example.todolist.data.ToDo
import com.example.todolist.utils.PrefsHelper
import com.example.todolist.viewmodel.ToDoViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private val viewModel: ToDoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.todoList)
        val addButton = findViewById<Button>(R.id.addButton)
        val logoutButton = findViewById<Button>(R.id.logoutButton)
        val titleField = findViewById<EditText>(R.id.titleField)
        val descField = findViewById<EditText>(R.id.descField)

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArrayList())
        listView.adapter = adapter

        viewModel.allTodos.observe(this, Observer { todos ->
            adapter.clear()
            adapter.addAll(todos.map { "${it.title}: ${it.description}" })
        })

        addButton.setOnClickListener {
            val title = titleField.text.toString()
            val desc = descField.text.toString()
            if (title.isNotEmpty() && desc.isNotEmpty()) {
                viewModel.addTodo(ToDo(title = title, description = desc))
                titleField.text.clear()
                descField.text.clear()
            }
        }

        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            PrefsHelper.clear(this)
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
