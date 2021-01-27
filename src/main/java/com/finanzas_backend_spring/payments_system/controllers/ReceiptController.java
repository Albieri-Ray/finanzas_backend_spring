package com.finanzas_backend_spring.payments_system.controllers;

import com.finanzas_backend_spring.payments_system.models.Receipt;
import com.finanzas_backend_spring.payments_system.resources.ReceiptResource;
import com.finanzas_backend_spring.payments_system.resources.SaveReceiptResource;

import com.finanzas_backend_spring.payments_system.services.ReceiptService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/")
public class ReceiptController{
    private final ReceiptService receiptService;
    private final ModelMapper mapper;

    @Autowired
    public ReceiptController(ReceiptService receiptService, ModelMapper mapper) {
        this.receiptService = receiptService;
        this.mapper = mapper;
    }

    @GetMapping("accounts/{id}/receipts/")
    public ResponseEntity<List<ReceiptResource>> getAllReceiptsByAccount(@PathVariable Long id){
        try {
            List<Receipt> receipts = receiptService.getAllReceiptByClient(id);
            if (receipts.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<ReceiptResource> receiptResources = receipts.stream().map(this::convertToResource).collect(Collectors.toList());
            return new ResponseEntity<>(receiptResources, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("accounts/{accountId}/receipts/")
    public ResponseEntity<ReceiptResource> create (@RequestBody SaveReceiptResource saveReceiptResource, @PathVariable Long accountId)
    {
        Receipt receipt = receiptService.save(convertToEntity(saveReceiptResource),accountId);
        return new ResponseEntity<>(convertToResource(receipt),HttpStatus.OK);
    }

    @GetMapping("receipt/{id}/")
    public ResponseEntity<ReceiptResource> getReceiptById(@PathVariable Long id){
        Receipt receipt = receiptService.getReceiptById(id);
        return new ResponseEntity<>(convertToResource(receipt),HttpStatus.OK);
    }

    public ReceiptResource convertToResource(Receipt entity) {
        return mapper.map(entity, ReceiptResource.class);
    }

    public Receipt convertToEntity(SaveReceiptResource resource) {
        return mapper.map(resource, Receipt.class);
    }
}
