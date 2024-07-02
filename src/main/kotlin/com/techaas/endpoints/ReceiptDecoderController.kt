package com.techaas.endpoints

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.techaas.dto.requests.DecodeReceiptRequest
import io.github.cdimascio.dotenv.Dotenv
import okhttp3.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import okhttp3.FormBody
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.util.*

import java.io.File
import java.io.IOException

data class Product(
    val id: Int,
    val name: String,
    val kcal: String,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double
)

@RestController
@RequestMapping("/receipt")
class ReceiptDecoderController() {
    final val dotenv = Dotenv.configure().directory(File(".").toString()).load()
    val token = dotenv["CHECK_TOKEN"]
    companion object {
        private var products: List<Product>

        init {
            val jsonFile = File("products.json")
            val jsonString = jsonFile.readText()
            val gson = Gson()
            val productListType = object : TypeToken<List<Product>>() {}.type
            products = gson.fromJson(jsonString, productListType)
        }
    }

    @PostMapping("/decode")
    fun getReceipt(@RequestBody decodeReceiptRequest: DecodeReceiptRequest): ResponseEntity<String> {
        val rawReceiptId = decodeReceiptRequest.rawReceiptId
        val url = "https://proverkacheka.com/api/v1/check/get"
        val client = OkHttpClient().newBuilder()
            .build()
        val formBody = FormBody.Builder()
            .add("qrraw", rawReceiptId)
            .add("token", token)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .addHeader("Cookie", "ENGID=1.1")
            .build()

        return try {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseBody = response.body?.string()
            val filteredResponse = filterJson(responseBody)
            ResponseEntity.ok(filteredResponse)
        } catch (e: IOException) {
            ResponseEntity.status(500).body("Error: ${e.message}")
        }
    }

    private fun filterJson(responseBody: String?): String {
        if (responseBody == null) return "{}"

        val json = JSONObject(responseBody)
        val data = json.getJSONObject("data").getJSONObject("json")

        val items = data.getJSONArray("items")
        val filteredItems = items.map { item ->
            val jsonItem = item as JSONObject
            val name = jsonItem.getString("name")

            val product = findProductByFirstWord(name)
            val kcal = product?.kcal ?: ""
            val proteins = product?.proteins ?: ""
            val fats = product?.fats ?: ""
            val carbohydrates = product?.carbohydrates ?: ""

            val weightPattern = "\\d+(\\.\\d+)?\\p{L}+".toRegex()
            val weightMatch = weightPattern.find(name)
            val weight = weightMatch?.value ?: ""
            val cleanedName = name.replace(weightPattern, "").trim()

            JSONObject().apply {
                put("name", cleanedName)
                put("weight", weight)
                put("kcal", kcal)
                put("proteins", proteins)
                put("fats", fats)
                put("carbohydrates", carbohydrates)
            }
        }

        val result = JSONObject()
        result.put("items", filteredItems)

        return result.toString(4)
    }

    private fun findProductByFirstWord(name: String): Product? {
        val searchWord = name.split(" ")[0].lowercase(Locale.getDefault())
        return products.find { it.name.lowercase(Locale.getDefault()).contains(searchWord) }
    }
}
