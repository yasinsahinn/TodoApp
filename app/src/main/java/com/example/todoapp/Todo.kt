package com.example.todoapp

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class Todo(var todo_id:String? = "",var konu:String ? = "",var aciklama:String? = "") : Serializable
{

}