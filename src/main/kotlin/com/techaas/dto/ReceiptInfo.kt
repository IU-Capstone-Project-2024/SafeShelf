package com.techaas.dto

data class Receipt(
    val items: List<Item>,
    val user: String,
    val dateTime: String,
    val totalSum: Int
)

data class Item(
    val nds: Int,
    val sum: Int,
    val name: String,
    val price: Int,
    val ndsSum: Int,
    val quantity: Int,
    val paymentType: Int,
    val productType: Int,
    val providerInn: String,
    val providerData: ProviderData,
    val itemsQuantityMeasure: Int,
    val paymentAgentByProductType: Int,
    val checkingProdInformationResult: Int? = null,
    val productCodeNew: ProductCodeNew? = null,
    val labelCodeProcesMode: Int? = null,
    val itemsIndustryDetails: ItemsIndustryDetails? = null
)

data class ProviderData(
    val providerName: String,
    val providerPhone: List<String>
)

data class ProductCodeNew(
    val gs1m: Gs1m
)

data class Gs1m(
    val gtin: String,
    val sernum: String,
    val productIdType: Int,
    val rawProductCode: String
)

data class ItemsIndustryDetails(
    val idFoiv: String,
    val industryPropValue: String,
    val foundationDocNumber: String,
    val foundationDocDateTime: String
)