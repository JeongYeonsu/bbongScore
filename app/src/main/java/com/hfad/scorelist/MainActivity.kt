package com.hfad.scorelist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var gameStartButton: Button? = null
    private var quickSettingButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Score"
        gameStartButton = findViewById(R.id.game_start_button)
        quickSettingButton = findViewById(R.id.default_setting_button)
        setViewAction()
    }

    private fun setViewAction() {
        gameStartButton!!.setOnClickListener {
            val gameNumberEdit: EditText = findViewById(R.id.game_count)
            val playerNumberEdit: EditText = findViewById(R.id.player_count)
            if (gameNumberEdit.text.toString() == "" || playerNumberEdit.text.toString() == "") {
                Toast.makeText(applicationContext, "Please Input Data", Toast.LENGTH_SHORT).show()
            } else {
                val gameNumber: Int = Integer.valueOf(gameNumberEdit.text.toString())
                val playerNumber: Int = Integer.valueOf(playerNumberEdit.text.toString())
                val intent = Intent(applicationContext, ShowTableActivity::class.java)
                intent.putExtra("players", playerNumber)
                intent.putExtra("games", gameNumber)
                startActivity(intent)
            }
        }

        quickSettingButton!!.setOnClickListener {
            val intent = Intent(applicationContext, SecondTableActivity::class.java)
            intent.putExtra("players", 4)
            intent.putExtra("games", 10)
            intent.putExtra("defaultSetting", true)
            startActivity(intent)
        }
    }
}