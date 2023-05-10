package com.example.elahorcadofjdj

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textViewWord: TextView
    private lateinit var editTextGuess: EditText
    private lateinit var buttonGuess: Button
    private lateinit var textViewResult: TextView
    private lateinit var textViewRemaining: TextView
    private lateinit var buttonNewGame: Button

    private val words = arrayOf("manzana", "platano", "cereza", "queso", "computadora", "pollo", "carro", "miel", "procesador")
    private var word = ""
    private var guessedLetters = mutableListOf<Char>()
    private var remainingAttempts = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewWord = findViewById(R.id.textViewWord)
        editTextGuess = findViewById(R.id.editTextGuess)
        buttonGuess = findViewById(R.id.buttonGuess)
        textViewResult = findViewById(R.id.textViewResult)
        textViewRemaining = findViewById(R.id.textViewRemaining)
        buttonNewGame = findViewById(R.id.buttonNewGame)

        buttonGuess.setOnClickListener {
            guessLetter()
        }

        buttonNewGame.setOnClickListener {
            newGame()
        }

        newGame()
    }

    private fun newGame() {
        word = words.random()
        guessedLetters.clear()
        remainingAttempts = 6

        updateWord()
        updateRemainingAttempts()
        updateResult("")
    }

    private fun guessLetter() {
        val letter = editTextGuess.text.toString().toLowerCase().getOrNull(0)
        editTextGuess.setText("")

        if (letter == null || letter !in 'a'..'z') {
            return
        }

        if (letter in guessedLetters) {
            updateResult("Ya has intentado esta letra, prueba con otra letra!")
            return
        }

        guessedLetters.add(letter)

        if (letter !in word) {
            remainingAttempts--
            updateRemainingAttempts()

            if (remainingAttempts == 0) {
                updateResult("Perdiste! La palabra correcta era: $word.")
                buttonNewGame.isEnabled = true
                return
            } else {
                updateResult("Incorrecto! Intenta de nuevo")
                return
            }
        }

        updateWord()

        if (word.all { it in guessedLetters }) {
            updateResult("Has ganado! La palabra es: $word.")
            buttonNewGame.isEnabled = true
            return
        }

        updateResult("Correcto!")
    }

    private fun updateWord() {
        val displayedWord = buildString {
            for (letter in word) {
                if (letter in guessedLetters) {
                    append(letter)
                } else {
                    append("_")
                }
                append(" ")
            }
        }

        textViewWord.text = displayedWord
    }

    private fun updateRemainingAttempts() {
        textViewRemaining.text = "Intentos disponibles: $remainingAttempts"
    }

    private fun updateResult(result: String) {
        textViewResult.text = result
    }
}