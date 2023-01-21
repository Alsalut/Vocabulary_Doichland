package com.example.vocabulary_doichland

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

const val keyMemory = "local_memory"
const val keyWords = "wo1rds"
const val keyAnswers = "answers"
const val strSplit = "&split&"
var countWord = 0
lateinit var mapWords: Map<String, String>
const val myTag = "myTag"

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

        // получаем строку из лок. памяти
        val strWords = sharedPreferences.getString(keyWords, "")
        val strAnswers = sharedPreferences.getString(keyAnswers, "")

        // заполняем массивы
        val arrWords = strWords!!.split(strSplit)
        val arrAnswers = strAnswers!!.split(strSplit)

        // заполняем Map
        mapWords = mapOf<String, String>()
        var listForShuffleIndexis = mutableListOf<Int>()
        Log.d(myTag, "Across map and list created")
        for (index in arrWords.indices)
        {
            mapWords = mapWords + Pair(arrWords[index], arrAnswers[index])
            listForShuffleIndexis.add(index)
        }

        // перемешиваем list
        listForShuffleIndexis.shuffle()

        // выводим первое случайное слово
        tv_word.text = arrWords[listForShuffleIndexis[countWord]]

        // при нажатии на tv_see_answer показываем в нём ответ
        tv_see_answer.setOnClickListener {
            tv_see_answer.text = mapWords[arrWords[listForShuffleIndexis[countWord]]]
        }


        // при нажатии на кнопку "Следующее слово" выводим по очереди все элементы Map
        btn_next_word.setOnClickListener {
            countWord = if (countWord == arrWords.size - 1) 0 else countWord + 1
            tv_see_answer.text = getText(R.string.str_see_answer)
            tv_word.text = arrWords[listForShuffleIndexis[countWord]]
        }

        // при нажатии на кнопку переходим в InputNewWord
        btn_add_new_word.setOnClickListener {
            startActivity(Intent(this, InputNewWord::class.java))
        }
    }

    // при нажатии "Назад" закрываем приложение и удаляем его из списка программ
    override fun onBackPressed()
    {
        super.onBackPressed()

        finishAndRemoveTask()
    }
}