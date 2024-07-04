package com.techaas.dto.qr

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
    val labelCodeProcessMode: Int? = null,
    val itemsIndustryDetails: ItemsIndustryDetails? = null
)