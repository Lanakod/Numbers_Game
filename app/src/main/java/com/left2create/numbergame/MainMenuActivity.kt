package com.left2create.numbergame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
    }

    fun difficultyClick(view: View)
    {
        val easyText = findViewById<Button>(R.id.easyButton).text
        val mediumText = findViewById<Button>(R.id.mediumButton).text
        val hardText = findViewById<Button>(R.id.hardButton).text
        val madnessText = findViewById<Button>(R.id.madnessButton).text
        when(findViewById<Button>(view.id).text)
        {
            easyText -> changeActivity(4)
            mediumText -> changeActivity(6)
            hardText -> changeActivity(8)
            madnessText -> changeActivity(10)
        }


    }

    private fun changeActivity(difficulty: Int){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("difficulty", difficulty)
        startActivity(intent)
    }

    fun infoClick(view: View)
    {
        Toast.makeText(this@MainMenuActivity, "Разработчик Малахов М.А.\nAKA Lanakod", Toast.LENGTH_LONG).show()
    }
}