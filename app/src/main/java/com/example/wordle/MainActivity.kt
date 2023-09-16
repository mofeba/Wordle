package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.view.Gravity


class MainActivity : AppCompatActivity() {
    private var guessCount = 0

    private lateinit var guesstext:EditText
    private lateinit var button: Button
    private lateinit var guess1val:TextView
    private lateinit var guess1actual:TextView
    private lateinit var guess2val:TextView
    private lateinit var guess2actual:TextView
    private lateinit var guess3val:TextView
    private lateinit var guess3actual:TextView
    private lateinit var correctWordView:TextView
    private lateinit var resetButton:Button
    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String, wordToGuess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
    private fun closeKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val word=FourLetterWordList

        guesstext=findViewById(R.id.guesstext)
        button=findViewById(R.id.button)
        // guesses
        guess1val=findViewById(R.id.guess1val)
        guess1actual=findViewById(R.id.guess1actual)
        guess2val=findViewById(R.id.guess2val)
        guess2actual=findViewById(R.id.guess2actual)
        guess3val=findViewById(R.id.guess3val)
        guess3actual=findViewById(R.id.guess3actual)
        resetButton=findViewById(R.id.resetButton)
        correctWordView=findViewById(R.id.correctWordView)

        var correctWord =word.getRandomFourLetterWord()
        button.setOnClickListener {
            val userGuessText = guesstext.text
            guesstext.setText("")     // clearing the text field
            closeKeyboard(guesstext)  // hiding the keyboard
            val userGuessEval = checkGuess(userGuessText.toString().uppercase(), correctWord)

            guessCount++

            if (guessCount == 1) {
                guess1val.text = userGuessText.toString().uppercase()
                guess1actual.text = userGuessEval

            } else if (guessCount == 2) {
                guess2val.text = userGuessText.toString().uppercase()
                guess2actual.text = userGuessEval

            } else if (guessCount == 3) {
                guess3val.text = userGuessText.toString().uppercase()
                guess3actual.text = userGuessEval



            }

            if (userGuessText.toString().uppercase() == correctWord) {  // user guessed correctly
                // show toast that user won
                val toast = Toast.makeText(this, "you won", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 10)
                toast.show()
                // show correct answer
                correctWordView.text = correctWord
                correctWordView.visibility = View.VISIBLE
                // disable submit button and text input field
                button.isEnabled = false
                guesstext.isEnabled = false
            } else if (guessCount == 3){  // user did not guess correctly on last try
                // show correct answer
                correctWordView.text = correctWord
                correctWordView.visibility = View.VISIBLE
                // disable submit button and text input field
                button.isEnabled = false
                guesstext.isEnabled = false
            }
            resetButton.setOnClickListener {
                resetButton.visibility = View.INVISIBLE
                guessCount = 0
                correctWord = word.getRandomFourLetterWord()

                guess1val.text = ""
                guess1actual.text = ""
                guess2val.text = ""
                guess2actual.text = ""
                guess3val.text = ""
                guess3actual.text = ""

                button.isEnabled = true
                guesstext.isEnabled = true
                correctWordView.visibility = View.INVISIBLE
            }
        }


    }
}