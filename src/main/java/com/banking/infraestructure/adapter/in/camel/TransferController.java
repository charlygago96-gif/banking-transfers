package com.banking.infraestructure.adapter.in.camel;

import org.apache.camel.ProducerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final ProducerTemplate producerTemplate;

    public TransferController(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    @PostMapping
    public ResponseEntity<Object> createTransfer(@RequestBody TransferRequest request) {
        Object result = producerTemplate.requestBody("direct:transfer", request);
        return ResponseEntity.ok(result);
    }
}