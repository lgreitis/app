package com.lukasgreicius.app

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Parser {
    val gson = Gson();
    val type = object : TypeToken<OpenExchangeRatesResponse>() {}.type
    fun parseString(text: String): OpenExchangeRatesResponse {
        return gson.fromJson<OpenExchangeRatesResponse>(text, type)
    }
}