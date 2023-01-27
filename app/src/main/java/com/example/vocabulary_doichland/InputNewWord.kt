package com.example.vocabulary_doichland

import android.content.Intent
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

        Log.d(myLog, "2 get intent")
        // получаем строку из Intent
        val strIntent = intent.getStringExtra(keyIntent)

        Log.d(myLog, "2 замена текста в et")
        // замена текста в et_new_foreign_word
        if (strIntent == strMissing) et_new_foreign_word.hint = "${getText(R.string.str_no_word)}\n${getText(R.string.str_foreign_word)}"

        // добавляем новый элемент в file
        btn_save_new_word.setOnClickListener {
            if (et_new_foreign_word.text.toString().isBlank() or et_new_answer.text.toString().isBlank()) return@setOnClickListener

            Log.d(myLog, "2 формируем новую строку")
            // если в памяти ничего не было, то... - иначе...
            val textNew =
                if (strIntent == strMissing) "${et_new_foreign_word.text}$splitKeyValue${et_new_answer.text}"
                else "${file.readText()}$splitEnd${et_new_foreign_word.text}$splitKeyValue${et_new_answer.text}"

            Log.d(myLog, "2 записываем новые данные в файл")
            // записываем новые данные в файл
            file.writeText(textNew)

            startActivity(Intent(this, MainActivity::class.java))

            finish()
        }
    }
}