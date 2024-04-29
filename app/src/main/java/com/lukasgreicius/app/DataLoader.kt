package com.lukasgreicius.app

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class DataLoader {
    private val parser = Parser()

    suspend fun makeGetRequest(url: String): OpenExchangeRatesResponse {
        return withContext(Dispatchers.IO) {
            val urlObj = URL(url)
            val connection = urlObj.openConnection() as HttpURLConnection
            try {
                connection.requestMethod = "GET"
                connection.connect()
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = reader.readText()
                reader.close()

                return@withContext parser.parseString(response)
            } finally {
                connection.disconnect()
            }
        }
    }
}