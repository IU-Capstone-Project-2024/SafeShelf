package com.techaas.domain.qra

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.techaas.data_entities.Product
import com.techaas.data_entities.ProductWithDate
import com.techaas.data_entities.ProductWithoutWeight
import com.techaas.dto.requests.DecodeReceiptRequest
import io.github.cdimascio.dotenv.Dotenv
import okhttp3.Request
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestBody
import java.util.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.math.BigDecimal
import java.sql.Timestamp

@Component
class QrAnalyzerService {
    final val dotenv = Dotenv.configure().directory(File(".").toString()).load()
    val token = dotenv["CHECK_TOKEN"]
    val url = dotenv["CHECK_URL"]

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

        val response = client.newCall(request).execute()
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        val responseBody = response.body?.string()
        val filteredResponse = filterJson(responseBody)
        return filteredResponse
    }

    private fun filterJson(responseBody: String?): List<ProductWithDate> {
        if (responseBody == null) return emptyList()

        val json = JSONObject(responseBody)
        val data = json.getJSONObject("data").getJSONObject("json")

        val items = data.getJSONArray("items")
        val filteredItems = items.mapNotNull { item ->
            val jsonItem = item as JSONObject
            val name = jsonItem.getString("name")

            val product = findProductByFirstWord(name)
            val kcal = product?.kcal ?: "0"
            val proteins = product?.proteins ?: 0.0
            val fats = product?.fats ?: 0.0
            val carbohydrates = product?.carbohydrates ?: 0.0

            val weightPattern = "\\d+(\\.\\d+)?\\p{L}+".toRegex()
            val weightMatch = weightPattern.find(name)
            val weight = weightMatch?.value?.let {
                it.replace("\\p{L}+".toRegex(), "") // remove the unit
            } ?: "0"
            val cleanedName = name.replace(weightPattern, "").trim()

            JSONObject().apply {
                put("id", product?.id ?: -1)
                put("name", cleanedName)
                put("weight", weight)
                put("kcal", kcal)
                put("proteins", proteins)
                put("fats", fats)
                put("carbohydrates", carbohydrates)
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

            ProductWithDate(
                id = id,
                name = name,
                kcal = kcal,
                proteins = proteins,
                fats = fats,
                carbohydrates = carbohydrates,
                weight = weight,
                date = Timestamp(System.currentTimeMillis())
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
