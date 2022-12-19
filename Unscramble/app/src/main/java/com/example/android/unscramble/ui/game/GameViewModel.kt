/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel containing the app data and methods to process the data
 */
class GameViewModel : ViewModel() {
    //사용자 점수
    private val _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    //현재 진행 중인 문제 번호
    private val _currentWordCount = MutableLiveData(0)
    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    //현재 출제 중인 배열이 섞인 단어
    private val _currentScrambledWord = MutableLiveData<String>()
    val currentScrambledWord: LiveData<String>
        get() = _currentScrambledWord

    // List of words used in the game
    private var usedWordsList: MutableList<String> = mutableListOf()

    //문자 배열이 섞이지 않은 현재 출제 중인 단어
    private lateinit var currentWord: String

    init {
        getNextWord()
    }

    /*
     * Updates currentWord and currentScrambledWord with the next word.
     * 이미 출제된 단어를 제외하고 새로운 단어로 문제를 출제하며, 몇번째 문제인지 카운트
     */
    private fun getNextWord() {
        //1. 랜덤으로 출제할 단어 선택
        currentWord = allWordsList.random()

        //2. 단어 배열 섞음
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        /*
         * 3.현재 단어 달라질 때까지 섞음
         * equals(string, ignoreCase) : Case Insensitive
         * 대소문자를 무시하고 문자열을 비교하려면 두번째 인자 ignoreCase 에 true 전달
         * false 를 전달하거나 입력하지 않으면 대소문자를 구분하여 비교
         */
        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }

        //4.1. 이전에 이미 출제된 단어라면 다른 단어 선택
        if (usedWordsList.contains(currentWord)) {
            getNextWord()
        } else {
            //4.2. 출제되지 않은 단어
            Log.d("Unscramble", "currentWord= $currentWord")
            //4.2.1 문제로 출제
            _currentScrambledWord.value = String(tempWord)

            /*
             * 4.2.2 몇번째 문제인지 카운트
             * inc() : Returns this value incremented by one.
             */
            _currentWordCount.value = _currentWordCount.value?.inc()

            //4.2.3 중복 출제를 방지하기 위해 출제된 단어 리스트에 업로드
            usedWordsList.add(currentWord)
        }
    }

    /*
     * Returns true if the current word count is less than MAX_NO_OF_WORDS
     * 마지막 문제(false)인지 체크하고 마지막 문제가 아니라면(true) 새로운 문제를 준비
     */
    fun nextWord(): Boolean {
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else {
            false
        }
    }

    /*
     * Re-initializes the game data to restart the game.
     * 게임 한세트가 끝난 후 사용자가 Play Again 을 누르면 새로운 게임을 준비
     */
    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        usedWordsList.clear()
        getNextWord()
    }

    /*
     * Returns true if the player word is correct.
     * Increases the score accordingly.
     */
    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    /*
     * Increases the game score if the player’s word is correct.
     */
    private fun increaseScore() {
        _score.value = _score.value?.plus(SCORE_INCREASE)
    }
}
