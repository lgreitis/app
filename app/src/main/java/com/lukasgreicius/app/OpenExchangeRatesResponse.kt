package com.lukasgreicius.app

import java.util.Dictionary

data class OpenExchangeRatesResponse(
    val disclaimer: String,
    val license: String,
    val timestamp: Number,
    val base: String,
    val rates: Map<String, Float>
)