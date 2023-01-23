package com.example.vocabulary_doichland

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

const val tag = "myTag"
const val keyMemory = "local_memory"
const val keyWords = "keyWords"
const val keyAnswers = "keyAnswers"
const val keyIntent = "keyIntent"
const val strSplit = "&split&"
const val strPlug = "strPlug" // строка-заглушка, если в sharedPreferences ничего нет
val mapWords = mutableMapOf<String, String>()
var indexMap = 0

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

        // устанавливаем индекс равным 0
        indexMap = 0

        // получаем строку из лок. памяти
        val sharedPreferences = getSharedPreferences(keyMemory, MODE_PRIVATE)

        // очищаем sharedPreferences
//        val editor = sharedPreferences.edit()
//        editor.clear()
//        editor.apply()

        val strWords = sharedPreferences.getString(keyWords, strPlug)
        val strAnswers = sharedPreferences.getString(keyAnswers, strPlug)

        // проверка наличия данных в памяти
        checkDataFromMemory(strWords)

        // заполняем Map
        fillMap(strWords, strAnswers, mapWords)
        
        // заполняем список рандомными ключами
        val listRandomKeys = mapWords.keys.shuffled(Random(System.currentTimeMillis()))

        // выводим первое случайное слово
        showWord(listRandomKeys)

        // при нажатии на tv_see_answer показываем в нём ответ
        tv_see_answer.setOnClickListener {
            showAnswer(mapWords, listRandomKeys)
        }

        // при нажатии на кнопку "Следующее слово" выводим следующий элемент, и далее по кругу
        btn_next_word.setOnClickListener {
            // задаём следующий индекс
            indexMap = if (indexMap == (mapWords.size - 1)) 0 else ++indexMap

            // показываем следующее слово
            showWord(listRandomKeys)
        }

        // при нажатии на кнопку "Добавить новое слово" переходим в InputNewWord
        btn_add_new_word.setOnClickListener {
            changeActivity(getString(R.string.str_foreign_word))
        }
    }

    // проверка наличия данных в памяти
    private fun checkDataFromMemory(strDataFromMemory: String?)
    {
        if (strDataFromMemory == strPlug) changeActivity(strDataFromMemory)
    }

    // переходим в InputNewWord со строкой-значением
    private fun changeActivity(strValue: String)
    {
        val intent = Intent(this, InputNewWord::class.java)
        intent.putExtra(keyIntent, strValue)
        startActivity(intent)
    }

    // заполняем Map
    private fun fillMap(strSplitKey: String?, strSplitValue: String?, mapValue: MutableMap<String, String>)
    {
        // заполняем массивы из строк
        val arrKey = strSplitKey?.split(strSplit)
        val arrValue = strSplitValue?.split(strSplit)

        // очищаем mapValue
        mapValue.clear()

        // из массивов заполняем Map
        for (index in 0 until arrKey!!.size) mapValue[arrKey[index]] = arrValue!![index]
    }

    // выводим случайное слово
    private fun showWord(listRandomKeys: List<String>)
    {
        // выводим в tv_word очередное слово
        tv_word.text = listRandomKeys[indexMap]

        // выводим в tv_see_answer "Увидеть ответ"
        tv_see_answer.text = getText(R.string.str_see_answer)
    }

    // выводим ответ
    private fun showAnswer(mapValue: MutableMap<String, String>, listRandomKeys: List<String>)
    {
        // выводим в tv_see_answer ответ
        tv_see_answer.text = mapValue[listRandomKeys[indexMap]]
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