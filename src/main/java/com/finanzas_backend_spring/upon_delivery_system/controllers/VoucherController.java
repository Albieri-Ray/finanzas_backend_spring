package com.finanzas_backend_spring.upon_delivery_system.controllers;

import com.finanzas_backend_spring.auth.resources.Message;
import com.finanzas_backend_spring.upon_delivery_system.models.Delivery;
import com.finanzas_backend_spring.upon_delivery_system.models.Voucher;
import com.finanzas_backend_spring.upon_delivery_system.resources.DeliveryResource;
import com.finanzas_backend_spring.upon_delivery_system.resources.SaveDeliveryResource;
import com.finanzas_backend_spring.upon_delivery_system.resources.VoucherResource;
import com.finanzas_backend_spring.upon_delivery_system.services.VoucherService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/")
public class VoucherController {
    private final VoucherService voucherService;
    private final ModelMapper mapper;
    @Autowired
    public VoucherController(VoucherService voucherService, ModelMapper mapper) {
        this.voucherService = voucherService;
        this.mapper = mapper;
    }

    @GetMapping("vouchers/")
    public ResponseEntity<Page<VoucherResource>> getAllVouchers (Pageable pageable){
        try
        {
            Page<Voucher> vouchers = voucherService.getAllVouchers(pageable);
            if(vouchers.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<VoucherResource> voucherResources = vouchers.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
            Page<VoucherResource> resources = new PageImpl<>(voucherResources, pageable, voucherResources.size());
            return new ResponseEntity<>(resources,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






    @GetMapping("deliveries/{id}/vouchers/")
    public ResponseEntity<Page<VoucherResource>> getAllVouchersByDelivery(@PathVariable Long id, Pageable pageable){
        try {
            Page<Voucher> vouchers = voucherService.getAllVouchersByDelivery(id, pageable);
            if(vouchers.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<VoucherResource> voucherResources = vouchers.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
            Page<VoucherResource> resources = new PageImpl<>(voucherResources, pageable, voucherResources.size());
            return new ResponseEntity<>(resources,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("deliveries/{deliveryId}/vouchers/")
    public ResponseEntity<VoucherResource> create (@PathVariable Long deliveryId)
    {
        Voucher voucher = voucherService.save(deliveryId);
        return new ResponseEntity<>(convertToResource(voucher),HttpStatus.OK);
    }

    @GetMapping("vouchers/{id}/")
    public ResponseEntity<VoucherResource> getVoucherById (@PathVariable Long id){
        Voucher voucher = voucherService.getVoucherById(id);
        return new ResponseEntity<>(convertToResource(voucher),HttpStatus.OK);
    }

    @DeleteMapping("vouchers/{id}/")
    public ResponseEntity<Message> delete (@PathVariable Long id){
        voucherService.delete(id);
        Message message = new Message("Delete successful");
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @PostConstruct
    public void init(){
        /*
        mapper.addMappings(new PropertyMap<Voucher, VoucherResource>() {
            @Override
            protected void configure() {
                map().setDeliveryName(source.getDelivery().getName());
                map().setDescription(source.getDelivery().getDescription());
            }
        });
        */
        mapper.typeMap(Voucher.class,VoucherResource.class).addMappings(mapper->mapper.map(src -> src.getDelivery().getName(),VoucherResource::setDeliveryName));
        mapper.typeMap(Voucher.class,VoucherResource.class).addMappings(mapper->mapper.map(src -> src.getDelivery().getDescription(),VoucherResource::setDescription));
    }

    public VoucherResource convertToResource(Voucher entity) {
        return mapper.map(entity, VoucherResource.class);
    }

}
