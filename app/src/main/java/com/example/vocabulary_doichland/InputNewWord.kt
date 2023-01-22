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

        // добавляем новый элемент в sharedPreferences
        btn_save_new_word.setOnClickListener {
            if (et_new_word.text.toString().isBlank() or et_new_answer.text.toString().isBlank()) return@setOnClickListener

            if (listWords.isNotEmpty())
            {
                editor.putString(keyWords, "${sharedPreferences.getString(keyWords, strPlug)}$strSplit${et_new_word.text}")
                editor.putString(keyAnswers, "${sharedPreferences.getString(keyAnswers, strPlug)}$strSplit${et_new_answer.text}")
            }
            else
            {
                editor.putString(keyWords, et_new_word.text.toString())
                editor.putString(keyAnswers, et_new_answer.text.toString())
            }

            editor.apply()
            finish()
        }
    }
}