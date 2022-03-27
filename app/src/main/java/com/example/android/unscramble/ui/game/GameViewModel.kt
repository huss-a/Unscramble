package com.example.android.unscramble.ui.game

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    var score = 0

    private var _currentWordCount = 0
    val currentWordCount get() = _currentWordCount

    private lateinit var _currentScrambledWord: String
    val currentScrambledWord get() = _currentScrambledWord

    private lateinit var _currentWord: String
    val currentWord get() = _currentWord

    private val usedWordsList = mutableListOf<String>()

    init {
        getNextWord()
    }

    /**
     * Reinitializes the data.
     */
    fun resetProperties() {
        score = 0
        _currentWordCount = 0
        usedWordsList.clear()
        getNextWord()
    }

    /**
     * Sets next word if currentWordCount is less than MAX_NO_OF_WORDS.
     * @return true if currentWordCount is less than MAX_NO_OF_WORDS, or else false.
     */
    fun nextWord(): Boolean {
        return if (currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    /**
     * Checks if User is correct and increases the score depending on it.
     * @return true if User is correct, or else false.
     */
    fun isUserCorrect(playerword: String): Boolean {
        return if (playerword.equals(currentWord, true)) {
            score += SCORE_INCREASE
            true
        } else false
    }

    /**
     * Select a random word, shuffle the characters and set the corresponding properties.
     */
    private fun getNextWord() {
        _currentWord = allWordsList.random()

        // Try again if usedWordsList contains currentWord
        if (usedWordsList.contains(_currentWord)) return getNextWord()
        val tempWord = _currentWord.toCharArray()
        tempWord.shuffle()

        // shuffle the word until it doesn't equal to the original one
        while (String(tempWord).equals(_currentWord, false)) {
            tempWord.shuffle()
        }
        ++_currentWordCount
        _currentScrambledWord = String(tempWord)
        usedWordsList.add(_currentWord)
    }
}