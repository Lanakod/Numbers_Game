package com.left2create.numbergame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    private val table: Array<Int> = Array(16) {-1}
    private var nullImg: ImageView? = null
    private var imgArray: Array<ImageView?> = arrayOfNulls(16)

    private var first = nullImg
    private var second = nullImg
    private var isFirst = false
    private var isSecond = false
    private var clickAmount = 0
    private var canChoose = true

    private var tvScore: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.textView).text = "%s %d".format("Количество кликов:\n", clickAmount)

        imgArray = arrayOf(findViewById(R.id.imageView),
                               findViewById(R.id.imageView2),
                               findViewById(R.id.imageView3),
                               findViewById(R.id.imageView4),
                               findViewById(R.id.imageView5),
                               findViewById(R.id.imageView6),
                               findViewById(R.id.imageView7),
                               findViewById(R.id.imageView8),
                               findViewById(R.id.imageView9),
                               findViewById(R.id.imageView10),
                               findViewById(R.id.imageView11),
                               findViewById(R.id.imageView12),
                               findViewById(R.id.imageView13),
                               findViewById(R.id.imageView14),
                               findViewById(R.id.imageView15),
                               findViewById(R.id.imageView16))
        tvScore = findViewById(R.id.textView)
        nullImg = findViewById(R.id.null_imageView)
        first = nullImg
        second = nullImg
        for (i in 0..15)
        {
            if (table[i] == -1)
            {
                table[i] = (0..9).random()
                var it = (0..15).random()
                while(table[it] != -1) it = (0..15).random()
                table[it] = table[i]
            }
        }
    }

    fun chooserClick(view: View)
    {
        if(canChoose)
        {
            if(!isFirst)
            {
                first = findViewById(view.id)
                setImage(first)
                isFirst = true
                tvScore?.text = "%s %d".format("Количество кликов:\n", ++clickAmount)
            }
            else if(first?.id != view.id)
            {
                second = findViewById(view.id)
                setImage(second)
                isSecond = true
                tvScore?.text = "%s %d".format("Количество кликов:\n", ++clickAmount)
            }
            if(isFirst && isSecond)
            {
                val firstNumber = table[imgArray.indexOf(first)]
                val secondNumber = table[imgArray.indexOf(second)]
                if(firstNumber == secondNumber)
                {
                    canChoose = false
                    hideImages(first, second)
                    valueCleaner()
                }
                else
                {
                    canChoose = false
                    resetImages(first, second)
                    valueCleaner()
                }
            }
        }
    }

    private fun valueCleaner()
    {
        first = nullImg
        second = nullImg
        isFirst = false
        isSecond = false
    }

    private fun resetImages(image1: ImageView?, image2: ImageView?)
    {
        Timer("ResetTimer", false).schedule(500) {
            image1?.setImageResource(R.drawable.empty)
            image2?.setImageResource(R.drawable.empty)
            canChoose = true
        }
    }

    private fun hideImages(image1: ImageView?, image2: ImageView?)
    {
        Timer("HideTimer", false).schedule(500) {
            image1?.visibility = View.INVISIBLE
            image2?.visibility = View.INVISIBLE
            canChoose = true
        }
    }

    private fun setImage(image: ImageView?)
    {
        when(table[imgArray.indexOf(image)])
        {
            0 -> image?.setImageResource(R.drawable.zero)
            1 -> image?.setImageResource(R.drawable.one)
            2 -> image?.setImageResource(R.drawable.two)
            3 -> image?.setImageResource(R.drawable.three)
            4 -> image?.setImageResource(R.drawable.four)
            5 -> image?.setImageResource(R.drawable.five)
            6 -> image?.setImageResource(R.drawable.six)
            7 -> image?.setImageResource(R.drawable.seven)
            8 -> image?.setImageResource(R.drawable.eight)
            9 -> image?.setImageResource(R.drawable.nine)
        }
    }

    fun restartClick(view: View)
    {
        val intent = intent
        finish()
        startActivity(intent)
    }

    fun infoClick(view: View)
    {
        Toast.makeText(this@MainActivity, "Разработчик Малахов М.А.\nAKA Lanakod", Toast.LENGTH_LONG).show()
    }
}