package net.perfectdreams.loritta.cinnamon.microservices.discordgatewayeventsprocessor.utils

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.Closeable
import java.io.File
import java.util.*
import kotlin.time.Duration.Companion.minutes

class Falatron(private val apiUrl: String) : Closeable {
    private val http = HttpClient {
        install(HttpTimeout) {
            connectTimeoutMillis = 180_000
            requestTimeoutMillis = 180_000
            socketTimeoutMillis  = 180_000
        }
    }

    /**
     * Generates an audio clip via Falatron
     *
     * @return the audio in MP3 format
     */
    suspend fun generate(voice: String, text: String): ByteArray {
        if (false) {
            val input = "{\"emojis\":\" \\ud83c\\udfb5 \\ud83d\\ude21 \\ud83d\\ude20 \\ud83d\\udc93 \\ud83c\\udfb6\",\"voice\":\"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LGBGFMCBfgv2j0sxrz2lzmxLHMQVUyoS1RMGMGGCEIjAovhAAIFgQaP/8+DEwW+MFizpnNAAI8SICMcOmzABQcGJiZQJZUW6oi5xhRzDV9I/O0FgAOZgYwXzEggFHgwWFwCOSCgkWUEIjwgAoI5GiIHDGuRtN5YpgQZa1Cssq+K8C/q8FXg0ErtZqVLFWvV1bRkYKhWPBAKJOA5JQAFAiOpEDdoaBDRQu8gqhQHDzLCyYArWgGLcAgIYUAX6RNmErGgKDwwxAGA1NC7T/LxYIlS0BUyqya0Evs7UjZQWtgQu/HYfQ5sogNDsuZCU3aGS5C/5MzyNoYtomCpskMRAUkUBg0Mbuk857QUVlGEEzW51JpvC/6SlZgTzxRaKlqZUpdl4EqS8KAWnTlX67NJPQS6qISpMQU1FMy4xMDCqqqqqqqoye0DkdIOhxQ2KVDDh5NNIcxoTDas4O13I2sgTDKONgs42StDTJ0MjhAwINTKhpM3HMyKOgEOkPTBARMLBkwoC0mQuAjBYWMkdM8dMwNSgAogxYBBsyic2Dk0aIyoZX5gCJnD5mBJYHGthHNpGuSkREEijLnjSojNihUGZ5ibqCaM4GDmyluTBBzECTDAwEBS5EAUyRszhkBHV4GDEGJDAYMx5CcYcYY8YBhzXy1JggYCCoatlAgUwwkBCV6qrAUEYkYYsIywAiDFhi9TdV0o7GNNGfPBgkQhDFijDgE+l4lnTAASyKKzNl4gQGYQGnCD/8+DE8HtUFawB3NAA0caFcbNkZIGMhjKmjOnDLjEozCGzWOzfPTMhhwSZY6Zw2ZQWlCCRhm0Rp0xjgrAACCMKEAwZWJYEAAzCCSySVzZQYBMIFL8iEQZtQa1cZIKm4YcUY0YYkEpIvqYgiZIqYUEr5NEwAcxQkHCUOxkTho0hjArDASAMGEMGAQWfcuqYAGXKU2a9Rvqw1dqpWmsxLglxW9YEoM+4MAmEElxnvYCX9Lao+ugCQRhQyVr1MSi0Rirci1QCBOoXWLRL+aSsVugFDGLFJIppFpkUnucFQFMVUrvTbKVSrpjCAowooxopHFYIssW2TqkbAUJKAVl0TUxQkoOtmEIAxZQy5SpMQU1FMy4xMDCqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqo=\"}"

            val falatronResponse = Json.decodeFromString<FalatronResponse>(input)

            return Base64.getDecoder().decode(falatronResponse.voice)
        }

        val response = withContext(Dispatchers.IO) {
            tryAndRepeat(voice, text)
        }

        val falatronResponse = Json.decodeFromString<FalatronResponse>(response)

        return Base64.getDecoder().decode(falatronResponse.voice)
    }

    private suspend fun tryAndRepeat(voice: String, text: String): String {
        var i = 0
        while (i != 5) {
            val response = withTimeout(3.minutes) {
                http.submitForm(
                    apiUrl,
                    Parameters.build {
                        this.append("voz", voice)
                        this.append("texto", text.take(300))
                    }
                )
            }

            if (response.status.isSuccess())
                return response.bodyAsText()

            i++
        }

        error("Failed to get Falatron voice after 5 tries!")
    }

    override fun close() {
        http.close()
    }

    @Serializable
    data class FalatronResponse(
        val emojis: String,
        val voice: String
    )
}