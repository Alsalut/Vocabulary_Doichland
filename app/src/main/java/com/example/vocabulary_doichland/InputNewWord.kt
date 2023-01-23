package com.example.vocabulary_doichland

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_input_new_word.*

class InputNewWord : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_new_word)

        val sharedPreferences = getSharedPreferences(keyMemory, MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // получаем строку из Intent
        val strIntent = intent.getStringExtra(keyIntent)

        // замена текста в et_new_foreign_word
        if(strIntent == strPlug) et_new_foreign_word.hint = "${getText(R.string.str_no_word)}\n${getText(R.string.str_foreign_word)}"

        // добавляем новый элемент в sharedPreferences
        btn_save_new_word.setOnClickListener {
            if (et_new_foreign_word.text.toString().isBlank() or et_new_answer.text.toString().isBlank()) return@setOnClickListener

            // если в памяти ничего не было, то... - иначе...
            if (strIntent == strPlug)
            {
                editor.putString(keyWords, et_new_foreign_word.text.toString())
                editor.putString(keyAnswers, et_new_answer.text.toString())
            }
            else
            {
                editor.putString(keyWords, "${sharedPreferences.getString(keyWords, strPlug)}$strSplit${et_new_foreign_word.text}")
                editor.putString(keyAnswers, "${sharedPreferences.getString(keyAnswers, strPlug)}$strSplit${et_new_answer.text}")
            }

            editor.apply()
            finish()
        }
    }
}