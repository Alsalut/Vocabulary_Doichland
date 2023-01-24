package com.example.vocabulary_doichland

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

const val keyMemory = "local_memory"
const val keyWords = "words"
const val keyAnswers = "answers"
const val strSplit = "&split&"
const val strPlug = "strPlug" // строка-заглушка, если в sharedPreferences ничего нет
var countWord = 0
val listWords = mutableListOf<String>()
val listAnswers = mutableListOf<String>()

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart()
    {
        super.onStart()

        val sharedPreferences = getSharedPreferences(keyMemory, MODE_PRIVATE)

        // очищаем sharedPreferences
//        val editor = sharedPreferences.edit()
//        editor.clear()
//        editor.apply()

        // получаем строку из лок. памяти
        val strWords = sharedPreferences.getString(keyWords, strPlug)
        val strAnswers = sharedPreferences.getString(keyAnswers, strPlug)

        // заполняем массивы
        val arrWords = strWords!!.split(strSplit)
        val arrAnswers = strAnswers!!.split(strSplit)

        // заполняем списки
        for (index in 0 until arrWords.size)
        {
            listWords.add(arrWords[index])
            listAnswers.add(arrAnswers[index])
        }

        // удаляем строки-заглушки strPlug
        listWords.remove(strPlug)
        listAnswers.remove(strPlug)

        // заполняем список-shuffled
        val listRandomIndex = mutableListOf<Int>()

        if (listWords.size == 0) listRandomIndex.add(0)
        else for (index in listWords.indices) listRandomIndex.add(index)

        // перемешиваем listRandomIndex
        listRandomIndex.shuffle()

        // выводим первое случайное слово
        if (listWords.size == 0)
        {
            tv_word.text = getText(R.string.str_no_word)
            tv_see_answer.visibility = View.INVISIBLE
            btn_next_word.visibility = View.INVISIBLE
        }
        else
        {
            tv_word.text = listWords[listRandomIndex[countWord]]
            tv_see_answer.visibility = View.VISIBLE
            btn_next_word.visibility = View.VISIBLE
        }

        // при нажатии на tv_see_answer показываем в нём ответ
        tv_see_answer.setOnClickListener {
            tv_see_answer.text = listAnswers[listRandomIndex[countWord]]
        }

        // при нажатии на кнопку "Следующее слово" выводим следующий элемент, и далее по кругу
        btn_next_word.setOnClickListener {
            countWord = if (countWord >= (listWords.size - 1)) 0 else (countWord + 1)
            tv_see_answer.text = getText(R.string.str_see_answer)
            tv_word.text = listWords[listRandomIndex[countWord]]
        }

        // при нажатии на кнопку "Добавить новое слово" переходим в InputNewWord
        btn_add_new_word.setOnClickListener {
            startActivity(Intent(this, InputNewWord::class.java))
        }
    }

    // при нажатии "Назад" закрываем приложение и удаляем его из списка программ
    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed()
    {
        super.onBackPressed()

        finishAndRemoveTask()
    }
}