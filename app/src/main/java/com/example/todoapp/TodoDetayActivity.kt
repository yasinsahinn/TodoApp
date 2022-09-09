package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.databinding.ActivityTodoDetayBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.HashMap

class TodoDetayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTodoDetayBinding
    private lateinit var todo: Todo
    private lateinit var refTodo: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTodoDetayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarTodoDetay.title = "Todo Details Screen"
        setSupportActionBar(binding.toolbarTodoDetay)

        val db = FirebaseDatabase.getInstance()
        refTodo = db.getReference("todo")

        todo = intent.getSerializableExtra("nesne") as Todo

        binding.editKonu.setText(todo.konu)
        binding.editAciklama.setText(todo.aciklama)
        binding.imageView3.setOnClickListener {
            Snackbar.make(binding.toolbarTodoDetay, "Are you sure you want to delete ? ", Snackbar.LENGTH_SHORT)
                .setAction("YES") {

                    refTodo.child(todo.todo_id.toString()).removeValue()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }.show()
        }
        binding.buttonKayit.setOnClickListener {
            val konu = binding.editKonu.text.toString().trim()
            val aciklama = binding.editAciklama.text.toString().trim()

            if (TextUtils.isEmpty(konu)) {
                Snackbar.make(binding.toolbarTodoDetay, "Konuyu giriniz ", Snackbar.LENGTH_SHORT).show()
            }
            if (TextUtils.isEmpty(aciklama)) {
                Snackbar.make(
                    binding.toolbarTodoDetay,
                    "Açıklamayı giriniz ",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            val bilgiler = HashMap<String, Any>()
            bilgiler.put("konu", konu)
            bilgiler.put("aciklama", aciklama)

            refTodo.child(todo.todo_id.toString()).updateChildren(bilgiler)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

}
