package com.techaas.endpoints

import com.techaas.data_entities.Product
import com.techaas.data_entities.ProductWithDate
import com.techaas.domain.qra.QrAnalyzerService
import com.techaas.dto.requests.DecodeReceiptRequest
import org.springframework.http.ResponseEntity
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
    fun getReceipt(@RequestBody decodeReceiptRequest: DecodeReceiptRequest): ResponseEntity<List<ProductWithDate>> {
        return ResponseEntity.ok(qrAnalyzerService.getReceipt(decodeReceiptRequest))
    }
}
