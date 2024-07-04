package com.techaas.endpoints

import com.techaas.dto.ProductWithDate
import com.techaas.dto.requests.DecodeReceiptRequest
import com.techaas.services.QrAnalyzerService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/receipt")
class ReceiptDecoderController(
    private val qrAnalyzerService: QrAnalyzerService
) {

    @PostMapping("/decode")
    fun getReceipt(@RequestBody decodeReceiptRequest: DecodeReceiptRequest): List<ProductWithDate> {
        return qrAnalyzerService.getReceipt(decodeReceiptRequest)
    }
}
