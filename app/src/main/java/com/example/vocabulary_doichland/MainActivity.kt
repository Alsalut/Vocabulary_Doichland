package com.example.vocabulary_doichland

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.nio.file.Path
import java.util.*

const val myLog = "myLog"
const val strEmpty = ""
const val strMissing = "&missing&"
const val keyIntent = "keyIntent"
const val splitKeyValue = "&splitKeyValue&"
const val splitEnd = "&splitEnd&"
const val fileName = "myFile.txt"
lateinit var path: Path
lateinit var file: File

class MainActivity : AppCompatActivity()
{
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // объявляем file
        fileInit(fileName)
    }

    // объявляем file
    @RequiresApi(Build.VERSION_CODES.O)
    private fun fileInit(fileName: String)
    {
        // указываем путь к папке, где лежит файл
        path = application.filesDir.toPath()

        // прописываем путь к файлу
        file = File("$path/$fileName")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart()
    {
        super.onStart()

        // проверяем состояние файла
        checkFile()
    }

    // проверяем состояние файла
    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkFile()
    {
        // если файл не существует, то создаём файл(записав в него любой текст)
        if (!file.exists()) file.writeText(strEmpty)

        // если файл пустой
        // то переходим в InputNewWord
        // и закрываем текущее Activity
        if (file.readText().isEmpty()) {
            changeActivity(strMissing)

            finish()
        }
    }

    override fun onResume()
    {
        super.onResume()

        // устанавливаем индекс равным 0
        var indexMap = 0

        // заполняем Map из файла
        val mapWords = readMapFromFile()

        // заполняем список рандомными ключами
        val listRandomKeys = mapWords.keys.shuffled(Random(System.currentTimeMillis()))

        // выводим первое случайное слово
        showWord(listRandomKeys[indexMap])

        // при нажатии на tv_see_answer показываем в нём ответ
        tv_see_answer.setOnClickListener {
            tv_see_answer.text = mapWords[listRandomKeys[indexMap]]
        }

        // при нажатии на кнопку "Следующее слово" выводим следующий элемент, и далее по кругу
        btn_next_word.setOnClickListener {
            // задаём следующий индекс
            indexMap = if (indexMap == (mapWords.size - 1)) 0 else ++indexMap

            // показываем следующее слово
            showWord(listRandomKeys[indexMap])
        }

        // при нажатии на кнопку "Добавить новое слово" переходим в InputNewWord
        btn_add_new_word.setOnClickListener {
            changeActivity(getString(R.string.str_foreign_word))
        }
    }

    // заполняем Map из файла
    private fun readMapFromFile(): MutableMap<String, String>
    {
        // разбиваем строку по "\n" и записываем в массив
        val arrKeyValue = file.readText().replace("\n", "").split(splitEnd)

        // объявляем Map
        val mapNew = mutableMapOf<String, String>()

        // разбиваем каждый элемент массива arrKeyValue по splitKeyValue
        // и сохраняем в Map как пару (Ключ, Значение)
        for (key_value in arrKeyValue) mapNew[key_value.split(splitKeyValue)[0]] = key_value.split(splitKeyValue)[1]

        // возвращаем Map
        return mapNew
    }

    // переходим в InputNewWord со строкой-значением
    private fun changeActivity(strValue: String)
    {
        // создаём intent
        val intent = Intent(this, InputNewWord::class.java)

        // помещаем в Intent строку strValue
        intent.putExtra(keyIntent, strValue)

        // переходим на другую Activity
        startActivity(intent)
    }

    // выводим случайное слово
    private fun showWord(nextWord: String)
    {
        // выводим в tv_word очередное слово
        tv_word.text = nextWord

        // выводим в tv_see_answer "Увидеть ответ"
        tv_see_answer.text = getText(R.string.str_see_answer)
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