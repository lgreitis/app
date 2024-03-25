package com.lukasgreicius.app

enum class CountVariant {
    WORDS, SYMBOLS
}

class WordCounter {
    companion object {
        fun countWords(input: String, variant: CountVariant): Int {
            return when (variant) {
                CountVariant.WORDS -> {
                    if (input.isBlank()) 0
                    else input.split(" ").size
                }
                CountVariant.SYMBOLS -> input.length
            }
        }
    }
}