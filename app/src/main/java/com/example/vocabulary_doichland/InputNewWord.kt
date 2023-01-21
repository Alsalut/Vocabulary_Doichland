package com.example.vocabulary_doichland

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_input_new_word.*

class InputNewWord : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_new_word)

        // добавляем новый элемент в map
        btn_save_new_word.setOnClickListener {
            if (et_new_word.text.toString().isBlank() or et_new_answer.text.toString()
                    .isBlank()
            ) return@setOnClickListener

            val sharedPreferences = getSharedPreferences(keyMemory, MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            Log.d(myTag, "Across arr created")
            editor.putString(keyWords, "${sharedPreferences.getString(keyWords,"")}$strSplit${et_new_word.text}" )
            editor.putString(keyAnswers, "${sharedPreferences.getString(keyAnswers,"")}$strSplit${et_new_answer.text}" )
            editor.apply()
            finish()
        }
    }
}