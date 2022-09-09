package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.example.todoapp.databinding.ActivityTodoKayitBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TodoKayit : AppCompatActivity() {
    private lateinit var binding:ActivityTodoKayitBinding
    private lateinit var refTodo: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTodoKayitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarTodoDetay.title = "Todo Save Screen"
        setSupportActionBar(binding.toolbarTodoDetay)

        val db = FirebaseDatabase.getInstance()
        refTodo= db.getReference("todo")

        binding.buttonKaydet.setOnClickListener {

            val konu= binding.editTextKonu.text.toString().trim()
            val aciklama = binding.editTextAciklama.text.toString().trim()

            if (TextUtils.isEmpty(konu))
            {
                Snackbar.make(binding.toolbarTodoDetay,"Konuyu giriniz", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(aciklama))
            {
                Snackbar.make(binding.toolbarTodoDetay,"Açıklama giriniz", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val todo = Todo("",konu,aciklama)

            refTodo.push().setValue(todo)

            val intent = Intent(this@TodoKayit,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    }
