package com.lukasgreicius.app

import org.junit.Test

import org.junit.Assert.*

class WordCounterUnitTest {
    @Test
    fun countWords_emptyStringWords() {
        var count = WordCounter.countWords("", CountVariant.WORDS)
        assertEquals(count, 0);
    }

    @Test
    fun countWords_emptyStringSymbols() {
        var count = WordCounter.countWords("", CountVariant.SYMBOLS)
        assertEquals(count, 0);
    }

    @Test
    fun countWords_correctWordCount() {
        var count = WordCounter.countWords("This should four words", CountVariant.WORDS)
        assertEquals(count, 4);
    }

    @Test
    fun countWords_correctSymbolCount() {
        var count = WordCounter.countWords("This should be thiry two symbols", CountVariant.SYMBOLS)
        assertEquals(count, 32);
    }

    @Test
    fun countWords_allSpacesWords() {
        var count = WordCounter.countWords("         ", CountVariant.WORDS)
        assertEquals(count, 0);
    }

    @Test
    fun countWords_spacesAreSymbols() {
        var count = WordCounter.countWords("   ", CountVariant.SYMBOLS)
        assertEquals(count, 3);
    }

    @Test
    fun countWords_specialCharactersAreSymbols() {
        var count = WordCounter.countWords(",./\\][=-)(*&^%$#@!`", CountVariant.SYMBOLS)
        assertEquals(count, 19);
    }
}
