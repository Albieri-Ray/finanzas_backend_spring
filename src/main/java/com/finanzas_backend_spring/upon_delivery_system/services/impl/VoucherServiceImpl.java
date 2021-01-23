package com.finanzas_backend_spring.upon_delivery_system.services.impl;

import com.finanzas_backend_spring.upon_delivery_system.models.Delivery;
import com.finanzas_backend_spring.upon_delivery_system.models.Voucher;
import com.finanzas_backend_spring.upon_delivery_system.repositories.DeliveryRepository;
import com.finanzas_backend_spring.upon_delivery_system.repositories.VoucherRepository;
import com.finanzas_backend_spring.upon_delivery_system.services.VoucherService;
import com.finanzas_backend_spring.user_system.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final DeliveryRepository deliveryRepository;
    private final LocalDate today = LocalDate.now();

    public VoucherServiceImpl(VoucherRepository voucherRepository, DeliveryRepository deliveryRepository) {
        this.voucherRepository = voucherRepository;
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public Page<Voucher> getAllVouchers(Pageable pageable) {
        return voucherRepository.findAll(pageable);
    }

    @Override
    public Page<Voucher> getAllVouchersByDelivery(Long deliveryId, Pageable pageable) {
        return voucherRepository.findByDeliveryId(deliveryId, pageable);
    }

    @Override
    public Voucher getVoucherById(Long id) {
        return voucherRepository.findById(id).orElseThrow(()->new NotFoundException("voucher","id",id));
    }

    @Override
    public Voucher save(Long deliveryId) {
        Voucher voucher = new Voucher();
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(()->new NotFoundException("delivery","id", deliveryId));
        voucher.setFrequency(delivery.getFrequency());
        voucher.setTotal((delivery.getPriceDelivery().add(delivery.getPriceProduct())));
        voucher.setRegisterDate(today);
        voucher.setDelivery(delivery);
        return voucherRepository.save(voucher);
    }

    @Override
    public void delete(Long id) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow(()->new NotFoundException("voucher","id",id));
        voucherRepository.delete(voucher);
    }
}
