package com.techaas.services

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.techaas.dto.ProductWithDate
import com.techaas.dto.ProductWithoutWeight
import com.techaas.dto.requests.DecodeReceiptRequest
import io.github.cdimascio.dotenv.Dotenv
import okhttp3.Request
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestBody
import java.util.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.math.BigDecimal
import java.time.format.DateTimeFormatter
import java.time.LocalDate

@Component
class QrAnalyzerService {
    final val dotenv = Dotenv.configure().directory(File(".").toString()).load()
    val token = dotenv["CHECK_TOKEN"]
    val url = dotenv["CHECK_URL"]
    val date_url = dotenv["CHECK_DATE"]

    val client = OkHttpClient().newBuilder()
        .build()

    companion object {
        private var products: List<ProductWithoutWeight>

        init {
            val jsonFile = File("products.json")
            val jsonString = jsonFile.readText()
            val gson = Gson()
            val productListType = object : TypeToken<List<ProductWithoutWeight>>() {}.type
            products = gson.fromJson(jsonString, productListType)
        }
    }

    fun getReceipt(@RequestBody decodeReceiptRequest: DecodeReceiptRequest): List<ProductWithDate> {
        val rawReceiptId = decodeReceiptRequest.rawReceiptId
        val formBody = FormBody.Builder()
            .add("qrraw", rawReceiptId)
            .add("token", token)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .addHeader("Cookie", "ENGID=1.1")
            .build()

        val response = client.newCall(request).execute()
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        val responseBody = response.body?.string()
        val filteredResponse = filterJson(responseBody)
        return filteredResponse
    }

    fun getDate(rawProductCode: String): LocalDate? {

        val request = Request.Builder()
            .url("$date_url?code=$rawProductCode")
            .get()
            .build()

        val response = client.newCall(request).execute()
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        val responseBody = response.body?.string()
        return findExpirationDate(JSONObject(responseBody))
    }

    fun findExpirationDate(json: JSONObject): LocalDate? {
        for (key in json.keys()) {
            val value = json.get(key)
            if (key == "expirationDate") {
                return LocalDate.parse(value.toString(), DateTimeFormatter.ISO_DATE_TIME)
            } else if (value is JSONObject) {
                val result = findExpirationDate(value)
                if (result != null) return result
            } else if (value is JSONArray) {
                for (i in 0 until value.length()) {
                    val element = value.get(i)
                    if (element is JSONObject) {
                        val result = findExpirationDate(element)
                        if (result != null) return result
                    }
                }
            }
        }
        return null
    }

    fun convertToStandardUnits(input: String): String {
        val regex = """(\d+(\.\d+)?)([a-zA-Zа-яА-Я]+)""".toRegex()
        val match = regex.find(input)
        if (match != null) {
            val (value, _, unit) = match.destructured
            val numericValue = value.toDouble()

            return when (unit.lowercase(Locale.getDefault())) {
                "l", "л" -> "${numericValue * 1000}"
                "kg", "кг" -> "${numericValue * 1000}"
                "г" -> "$numericValue"
                "мл" -> "$numericValue"
                else -> "0"
            }
        }
        return "0"
    }

    private fun filterJson(responseBody: String?): List<ProductWithDate> {
        if (responseBody == null) return emptyList()

        val json = JSONObject(responseBody)
        val data = json.getJSONObject("data").getJSONObject("json")

        val items = data.getJSONArray("items")
        val filteredItems = items.mapNotNull { item ->
            val jsonItem = item as JSONObject
            var date: String? = null
            val name = jsonItem.getString("name")
            if (jsonItem.has("productCodeNew")) {
                val productCodeNew = jsonItem.getJSONObject("productCodeNew")
                if (productCodeNew.has("gs1m")) {
                    val gs1m = productCodeNew.getJSONObject("gs1m")
                    if (gs1m.has("rawProductCode")) {
                        val rawProductCode = gs1m.getString("rawProductCode")
                        date = getDate(rawProductCode).toString()
                    }
                }
            }
            val product = findProductByFirstWord(name)
            val kcal = product?.kcal ?: "0"
            val proteins = product?.proteins ?: 0.0
            val fats = product?.fats ?: 0.0
            val carbohydrates = product?.carbohydrates ?: 0.0

            val weightPattern = "\\d+(\\.\\d+)?\\p{L}+".toRegex()
            val weightMatch = weightPattern.find(name)
            val weight = weightMatch?.value?.let { convertToStandardUnits(it) }
            val cleanedName = name.replace(weightPattern, "").trim()

            JSONObject().apply {
                put("id", product?.id ?: -1)
                put("name", cleanedName)
                put("weight", weight)
                put("kcal", kcal)
                put("proteins", proteins)
                put("fats", fats)
                put("carbohydrates", carbohydrates)
                put("date", date.toString())
            }
        }

        return filteredItems.map { item ->
            val jsonItem = item

            val id = jsonItem.getInt("id")
            val name = jsonItem.getString("name")
            val kcal = jsonItem.optBigDecimal("kcal")
            val proteins = jsonItem.optBigDecimal("proteins")
            val fats = jsonItem.optBigDecimal("fats")
            val carbohydrates = jsonItem.optBigDecimal("carbohydrates")
            val weight = jsonItem.optBigDecimal("weight")
            var date: LocalDate = LocalDate.now()
            if (jsonItem["date"] != "null") {
                date = (jsonItem["date"] as? String)?.let { LocalDate.parse(it, DateTimeFormatter.ISO_DATE) }!!
            }
            ProductWithDate(
                id = id,
                name = name,
                kcal = kcal,
                proteins = proteins,
                fats = fats,
                carbohydrates = carbohydrates,
                weight = weight,
                date = date
            )
        }
    }

    private fun JSONObject.optBigDecimal(key: String): BigDecimal {
        return when (val value = this.opt(key)) {
            is Number -> BigDecimal(value.toString())
            is String -> BigDecimal(value)
            else -> BigDecimal.ZERO
        }
    }

    private fun findProductByFirstWord(name: String): ProductWithoutWeight? {
        val searchWord = name.split(" ")[0].lowercase(Locale.getDefault())
        return products.find { it.name.lowercase(Locale.getDefault()).contains(searchWord) }
    }
}
