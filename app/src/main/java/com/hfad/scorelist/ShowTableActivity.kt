package com.hfad.scorelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.ActivityInfo
import android.view.ViewGroup
import android.view.Gravity
import android.graphics.Color
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import java.util.ArrayList

class ShowTableActivity : AppCompatActivity() {
    var isLineCalculated: ArrayList<Boolean>? = null
    private var tableLayout: TableLayout? = null
    private var getScoreButton: Button? = null
    private var resetButton: Button? = null
    private var games = 0
    private var players = 0
    private var isDefaultSetting = false
    private var defaultName = arrayOf("아빠", "엄마", "선미", "연수")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_table_layout)
        //
        //
        // requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        removeAppBar()
        isLineCalculated = ArrayList()
        tableLayout = findViewById(R.id.score_table)
        getScoreButton = findViewById(R.id.get_score_button)
        resetButton = findViewById(R.id.resetButton)
        setButtonClickListener()
        resolveIntent()
        makeRank()
        makeHead()
        makeScoreRow()
        makeBody()
    }

    private fun removeAppBar() {
        val actionBar = supportActionBar
        actionBar!!.hide()
    }

    private fun resolveIntent() {
        val intent = intent
        players = intent.getIntExtra("players", 1)
        games = intent.getIntExtra("games", 10)
        isDefaultSetting = intent.getBooleanExtra("defaultSetting", false);
    }

    private fun setButtonClickListener() {
        getScoreButton?.setOnClickListener { calculatorScore() }
        resetButton?.setOnClickListener{ resetScore() }
    }

    private fun calculatorScore() {
        val totalData = players * games
        val playerScore = ArrayList<Int>()
        for (i in 0 until players) {
            playerScore.add(0)
        }
        for (i in 0 until totalData) {
            val pid = i % players
            val editText = findViewById<EditText>(getID(i))
            if (editText.text.toString() == "") {
                //Log.d("TEST123", "Exception!!")
                continue
            }
            val score = editText.text.toString().toInt()
            var pScore = playerScore[pid]
            pScore += score
            playerScore[pid] = pScore
        }
        for (i in 0 until players) {
            val sc = playerScore[i].toString()
            //Log.d("TEST123", "player " + i + " : " + sc);
            val pScoreView = findViewById<EditText>(getPlayerScoreID(i))
            pScoreView.setText(sc)
        }

        getRank(playerScore)
    }

    private fun getRank(playScore : ArrayList<Int>) {
        val rank = ArrayList<Int>()
        for (i in 0 until players) {
            rank.add(0)
        }

        for (i in 0 until playScore.size) {
           rank[i] = 1
           for (j in 0 until playScore.size) {
               if (playScore[i] > playScore[j]) {
                   rank[i]++
               }
           }
        }

//        for (i in 0 until rank.size) {
//            Log.d("TEST123", "player " + i + " : " + rank[i])
//        }

        for (i in 0 until players) {
            val rankResult = findViewById<TextView>(getRankID(i))
            rankResult.text = rank[i].toString() + " 등"
        }
    }

    private fun resetScore() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("reset Score")
        alertDialogBuilder.setMessage("Really Score?")
            .setCancelable(false)
            .setPositiveButton(
                "Do Reset"
            ) { _, _ -> doResetScore() }
            .setNegativeButton(
                "Cancel"
            ) { dialogInterface, _ -> dialogInterface.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun doResetScore() {
        val totalData = players * games

        for (i in 0 until totalData) {
            //val pid = i % players
            val editText = findViewById<EditText>(getID(i))
            editText.setText("")
            editText.setBackgroundColor(Color.TRANSPARENT)
        }
        for (i in 0 until players) {
            val pScoreView = findViewById<EditText>(getPlayerScoreID(i))
            pScoreView.setText("0")
        }
    }

    private fun getID(i: Int): Int {
        return resources.getIdentifier("val$i", "id", packageName)
    }

    private fun getID(r: Int, c: Int): Int {
        val num = r * players + c
        return resources.getIdentifier("val$num", "id", packageName)
    }

    private fun getPlayerScoreID(i: Int): Int {
        return resources.getIdentifier("player_score_$i", "id", packageName)
    }

    private fun getLineCalculatorID(i: Int): Int {
        return resources.getIdentifier("lineCal_$i", "id", packageName)
    }

    private fun getPlayersNameID(i: Int): Int {
        return resources.getIdentifier("player_name_$i", "id", packageName)
    }

    private fun getRankID(i : Int): Int {
        return resources.getIdentifier("rank_$i", "id", packageName)
    }

    private fun makeHead() {
        val headRow = TableRow(this)
        headRow.layoutParams = TableRow.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT
            //ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val firstEmpty = TextView(this)
       // firstEmpty.layoutParams = LinearLayout.LayoutParams(10, ViewGroup.LayoutParams.WRAP_CONTENT)
        headRow.addView(firstEmpty)
        for (i in 0 until players) {
            val nameView = EditText(this)
            nameView.id = getPlayersNameID(i)

            nameView.hint = "Players"

            if (isDefaultSetting) {
                nameView.setText(defaultName[i])
            }
//            if (players == 4) {
//                nameView.setText(defaultName[i])
//            } else {
//                nameView.hint = "Players"
//            }
            nameView.textSize = 15f
            nameView.gravity = Gravity.CENTER
            nameView.setBackgroundColor(resources.getColor(R.color.colorHeader, theme))
            headRow.addView(nameView)
        }
        tableLayout!!.addView(headRow)
    }

    private fun makeRank() {
        val rankRow = TableRow(this)
        rankRow.layoutParams = TableRow.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val firstEmpty = TextView(this)
        rankRow.addView(firstEmpty)

        for (i in 0 until players) {
            val rankView = TextView(this)
            rankView.id = getRankID(i)
            rankView.textSize = 15f
            rankView.gravity = Gravity.CENTER
            //rankView.setBackgroundColor(resources.getColor(R.color.colorHeader, theme))
            rankRow.addView(rankView)
        }
        tableLayout!!.addView(rankRow)
    }

    private fun makeScoreRow() {
        val scoreRow = TableRow(this)
        scoreRow.layoutParams = TableRow.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT
            //ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val firstEmpty = TextView(this)
        //firstEmpty.layoutParams = LinearLayout.LayoutParams(10, ViewGroup.LayoutParams.WRAP_CONTENT)
        firstEmpty.text = "총점"
        firstEmpty.gravity = Gravity.CENTER
        firstEmpty.inputType = InputType.TYPE_CLASS_NUMBER
        scoreRow.addView(firstEmpty)
        for (i in 0 until players) {
            val nameView = EditText(this)
            nameView.id = getPlayerScoreID(i)
            nameView.textSize = 22f
            nameView.gravity = Gravity.CENTER
            nameView.setBackgroundColor(resources.getColor(R.color.colorScore, theme))
            scoreRow.addView(nameView)
        }
        tableLayout!!.addView(scoreRow)
    }

    private fun makeBody() {
        for (j in 0 until games) {
            isLineCalculated!!.add(false)
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
                //ViewGroup.LayoutParams.WRAP_CONTENT
            )
            //val firstEmpty = TextView(this)
            val firstEmpty = Button(this)
            //firstEmpty.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            firstEmpty.id = getLineCalculatorID(j)
            firstEmpty.text = (j + 1).toString()
            //firstEmpty.setEms(1)
            firstEmpty.textSize = 20f
            firstEmpty.gravity = Gravity.CENTER
            firstEmpty.setOnClickListener { view -> onClickLineCalculator(view) }
            tableRow.addView(firstEmpty)
            for (i in 0 until players) {
                val editText = EditText(this)
                editText.id = getID(j, i)
                editText.inputType = InputType.TYPE_CLASS_NUMBER
                editText.setEms(4)
                //editText.setText("0")
                editText.gravity = Gravity.CENTER
                editText.setBackgroundColor(Color.TRANSPARENT)
                tableRow.addView(editText)
            }
            tableLayout!!.addView(tableRow)
        }
    }

    private fun onClickLineCalculator(v: View) {
        val `val` = (v as TextView).text.toString()
        var line = Integer.valueOf(`val`)
        line--
        if (isLineCalculated!![line]) {
            for (i in 0 until players) {
                val id = line * players + i
                val editText = findViewById<EditText>(getID(id))
                editText.setBackgroundColor(Color.TRANSPARENT)
            }
            isLineCalculated!![line] = false
        } else {
            var minEditTextId = 0
            var minVal = 10000
            for (i in 0 until players) {
                val id = line * players + i
                val editText = findViewById<EditText>(getID(id))
                if (editText.text.toString() == "") continue
                var score = Integer.valueOf(editText.text.toString())
                if (score > 10000) {
                    score -= 10000
                    score *= -1
                    editText.setText(score.toString())
                }
                if (score < minVal) {
                    minVal = score
                    minEditTextId = id
                }
            }
            val minEditText = findViewById<EditText>(getID(minEditTextId))
            minEditText.setBackgroundColor(applicationContext.getColor(R.color.backupColor3))
            isLineCalculated!![line] = true
        }
    }

    override fun onBackPressed() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Exit")
        //        alertDialogBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
//                if (i == KeyEvent.KEYCODE_B) {
//                    Log.d("TEST123", "onCLICK!");
//                    return true;
//                }
//                return false;
//            }
//        });
        alertDialogBuilder.setMessage("Really Exit ?")
            .setCancelable(false)
            .setPositiveButton(
                "Exit"
            ) { _, _ -> super@ShowTableActivity.onBackPressed() }
            .setNegativeButton(
                "Cancel"
            ) { dialogInterface, _ -> dialogInterface.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}