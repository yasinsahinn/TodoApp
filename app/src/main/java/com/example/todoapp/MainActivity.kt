package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var todoList: ArrayList<Todo>
    private lateinit var adapter: TodoAdapter
    private lateinit var refTodo: DatabaseReference
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = "Todo App Main Screen"
        setSupportActionBar(binding.toolbar)

        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = LinearLayoutManager(this@MainActivity)

        val db = FirebaseDatabase.getInstance()
        refTodo = db.getReference("todo")

        todoList = ArrayList()

        adapter = TodoAdapter(this, todoList)

        binding.rv.adapter = adapter

        tumNotlar()

        binding.fabBtn.setOnClickListener {

            val intent = Intent(this@MainActivity, TodoKayit::class.java)
            startActivity(intent)
        }

        binding.imageView.setOnClickListener {
            firebaseAuth.signOut()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun tumNotlar() {
        refTodo.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                todoList.clear()
                for (i in snapshot.children) {
                    val todo = i.getValue(Todo::class.java)

                    if (todo != null) {
                        todo.todo_id = i.key
                        todoList.add(todo)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}