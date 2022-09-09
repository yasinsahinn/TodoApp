package com.example.todoapp

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(private val mContext:Context,private val todoList:List<Todo>)
    :RecyclerView.Adapter<TodoAdapter.CardTasarimTutucu>()
{
    inner class CardTasarimTutucu(tasarim:View) : RecyclerView.ViewHolder(tasarim) {
        var todo_card: CardView
        var textViewKonu: TextView
        var textViewAciklama: TextView
        var checkBox:CheckBox

        init
        {
            todo_card = tasarim.findViewById(R.id.todo_card)
            textViewKonu = tasarim.findViewById(R.id.textViewKonu)
            textViewAciklama = tasarim.findViewById(R.id.textViewAciklama)
            checkBox = tasarim.findViewById(R.id.checkBox)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val tasarim = LayoutInflater.from(mContext).inflate(R.layout.card_tasarim,parent,false)
        return CardTasarimTutucu(tasarim)
    }

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val todo = todoList.get(position)

        holder.textViewKonu.text = todo.konu
        holder.textViewAciklama.text = todo.aciklama

        holder.todo_card.setOnClickListener {
            val intent = Intent(mContext,TodoDetayActivity::class.java)
            intent.putExtra("nesne",todo)
            mContext.startActivity(intent)
        }
        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
            {
                holder.textViewKonu.setPaintFlags(holder.textViewKonu.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                holder.textViewAciklama.setPaintFlags(holder.textViewAciklama.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
            }
            else
            {
                holder.textViewKonu.setPaintFlags(holder.textViewKonu.getPaintFlags() and Paint.STRIKE_THRU_TEXT_FLAG.inv())
                holder.textViewAciklama.setPaintFlags(holder.textViewAciklama.getPaintFlags() and Paint.STRIKE_THRU_TEXT_FLAG.inv())
            }
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }
}