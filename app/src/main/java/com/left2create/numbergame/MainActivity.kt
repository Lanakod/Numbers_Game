package com.left2create.numbergame

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.*
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var table: Array<Int>
    private lateinit var nullImg: ImageView
    private lateinit var imgArray: Array<ImageView?>

    private lateinit var first: ImageView
    private lateinit var second: ImageView
    private var isFirst = false
    private var isSecond = false
    private var score = 0
    private var combo = 0
    private var canChoose = true
    private var size = 0

    private lateinit var tvScore: TextView

    private lateinit var layoutGrid: androidx.gridlayout.widget.GridLayout

    private var createdImageId = 3228000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(intent.hasExtra("difficulty")) size = intent.getIntExtra("difficulty", 0)

        tvScore = findViewById(R.id.textView)
        tvScore.text = "%s %d %s %d".format("Счёт: ", score, "\nКомбо: ", combo)

        layoutGrid = findViewById(R.id.gridLayout)


        layoutGrid.columnCount = size
        layoutGrid.rowCount = size
        table = Array(size*size) {-1}
        imgArray = arrayOfNulls(size*size)

        nullImg = findViewById(R.id.null_imageView)
        first = nullImg
        second = nullImg
        for (i in 0 until size*size)
        {
            if (table[i] == -1)
            {
                table[i] = (0..9).random()
                var it: Int
                do it = (0 until size*size).random()
                while(table[it] != -1)
                table[it] = table[i]
            }

            imgArray[i] = findViewById(imageCreator())
        }
    }

    private fun imageCreator(): Int{
        val createdImage = ImageView(this)
        var imgSize = 0
        when(size)
        {
            4 -> imgSize = 85.dpToPixels(this).roundToInt()
            6 -> imgSize = 62.dpToPixels(this).roundToInt()
            8 -> imgSize = 45.dpToPixels(this).roundToInt()
            10 -> imgSize = 35.dpToPixels(this).roundToInt()
        }

        createdImage.setImageResource(R.drawable.empty)
        createdImage.layoutParams = LinearLayout.LayoutParams(imgSize, imgSize)
        createdImage.setOnClickListener{ chooserClick(createdImage) }
        createdImage.id = createdImageId++
        layoutGrid.addView(createdImage)
        return createdImage.id
    }

    private fun chooserClick(view: View)
    {
        if(canChoose)
        {
            if(!isFirst)
            {
                first = findViewById(view.id)
                setImage(first)
                isFirst = true
            }
            else if(first.id != view.id)
            {
                second = findViewById(view.id)
                setImage(second)
                isSecond = true
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

                    score += 100 + 50*combo++
                    tvScore.text = "%s %d %s %d".format("Счёт: ", score, "\nКомбо: ", combo)
                }
                else
                {
                    canChoose = false
                    resetImages(first, second)
                    valueCleaner()
                    if(score >= 10) score -= 10
                    else score = 0
                    combo = 0
                    tvScore.text = "%s %d %s %d".format("Счёт: ", score, "\nКомбо: ", combo)
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

    // extension function to convert dp to equivalent pixels
    private fun Int.dpToPixels(context: Context):Float = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,this.toFloat(),context.resources.displayMetrics
    )
}