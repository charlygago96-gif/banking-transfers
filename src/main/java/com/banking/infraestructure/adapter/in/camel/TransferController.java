package com.banking.infrastructure.adapter.in.camel;

import com.banking.infraestructure.adapter.in.camel.TransferRequest;
import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final ProducerTemplate producerTemplate;

    @PostMapping
    public ResponseEntity<Object> createTransfer(@RequestBody TransferRequest request) {
        Object result = producerTemplate.requestBody("direct:transfer", request);
        return ResponseEntity.ok(result);
    }
}