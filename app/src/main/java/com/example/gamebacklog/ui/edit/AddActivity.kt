package com.example.gamebacklog.ui.edit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.gamebacklog.R
import com.example.gamebacklog.model.Game

import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*
import java.util.*

const val EXTRA_GAME = "EXTRA_GAME"

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)

        initviews()

    }

    private fun initviews() {
        fab.setOnClickListener {
            validatefields()
        }
    }

    private fun validatefields()    {
        val title = etTitle.text.toString()
        val platform = etPlatform.text.toString()
        val day = etDay.text.toString()
        val month = etMonth.text.toString()
        val year = etYear.text.toString()

        if (
            title.isNotBlank() && platform.isNotBlank() &&
                    day.isNotBlank() && month.isNotBlank() &&
                    year.isNotBlank()
                )   {
            // All fields filled in
            //http://www.java2s.com/Code/Android/Date-Type/Createdatefromyearmonthanddayvalue.htm
            val cal = Calendar.getInstance()

            cal.set(year.toInt(),month.toInt(),day.toInt())

            val game = Game(title,platform,cal.time)
            val resultIntent = Intent()

            resultIntent.putExtra(EXTRA_GAME, game)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()

        }
        else    {
            // Not all fields filled in
            Toast.makeText(this,"All fields must be filled in",Toast.LENGTH_LONG).show()
        }
    }

}
