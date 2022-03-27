package com.example.android.unscramble.ui.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private var _score = MutableLiveData(0)
    val score get() = _score

    private var _currentWordCount = MutableLiveData(0)
    val currentWordCount get() = _currentWordCount

    private val _currentScrambledWord = MutableLiveData<String>()
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
        score.value = 0
        _currentWordCount.value = 0
        usedWordsList.clear()
        getNextWord()
    }

    /**
     * Sets next word if currentWordCount is less than MAX_NO_OF_WORDS.
     * @return true if currentWordCount is less than MAX_NO_OF_WORDS, or else false.
     */
    fun nextWord(): Boolean {
        return if (currentWordCount.value!! < MAX_NO_OF_WORDS) {
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
            score.value = score.value?.plus(SCORE_INCREASE)
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
        _currentWordCount.value = _currentWordCount.value?.inc()
        _currentScrambledWord.value = String(tempWord)
        usedWordsList.add(_currentWord)
    }
}