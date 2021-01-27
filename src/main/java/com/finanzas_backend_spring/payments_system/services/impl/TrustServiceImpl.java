package com.finanzas_backend_spring.payments_system.services.impl;

import com.finanzas_backend_spring.accounts_system.models.Account;
import com.finanzas_backend_spring.accounts_system.repositories.AccountRepository;
import com.finanzas_backend_spring.payments_system.models.Trust;
import com.finanzas_backend_spring.payments_system.repositories.TrustRepository;
import com.finanzas_backend_spring.payments_system.services.TrustService;
import com.finanzas_backend_spring.payments_system.util.FullLimitException;
import com.finanzas_backend_spring.user_system.util.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TrustServiceImpl implements TrustService{
    private final TrustRepository trustRepository;
    private final AccountRepository accountRepository;
    private final LocalDate today = LocalDate.now();

    @Autowired
    public TrustServiceImpl(TrustRepository trustRepository, AccountRepository accountRepository) {
        this.trustRepository = trustRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Trust> getAllTrusts() {
        return trustRepository.findAll();
    }

    @Override
    public List<Trust> getAllTrustByAccount(Long accountId) {
        return trustRepository.findByAccountId(accountId);
    }

    @Override
    public Trust getTrustById(Long id) {
        return trustRepository.findById(id).orElseThrow(()->new NotFoundException("trust","id",id));
    }

    @Override
    public Trust save(Trust trust, Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(()->new NotFoundException("account","id",accountId));
        if (verifyIfIsPossible(account,trust.getPriceProduct())){
            trust.setRegisterDate(today);
            trust.setAccount(account);
            return trustRepository.save(trust);
        }else{
            throw new FullLimitException(trust.getPriceProduct().toString());
        }
    }

    @Override
    public Trust update(Long id, Trust trust) {
        Trust existed = trustRepository.findById(id).orElseThrow(()->new NotFoundException("trust","id",id));
        existed.setPriceProduct(trust.getPriceProduct());
        existed.setProducts(trust.getProducts());
        return trustRepository.save(existed);
    }

    @Override
    public void delete(Long id) {
        Trust trust = trustRepository.findById(id).orElseThrow(()->new NotFoundException("trust","id",id));
        trustRepository.delete(trust);
    }

    @Override
    public void deleteAllByAccount(Long accountId) {
        trustRepository.deleteAllByAccountId(accountId);
    }

    private Boolean verifyIfIsPossible(Account account, BigDecimal priceProduct){
        BigDecimal credit = account.getLineOfCredit().getCredit();
        BigDecimal result =new BigDecimal(0);
        List<Trust> trusts = trustRepository.findByAccountId(account.getId());
        if (trusts.isEmpty()) {
            return priceProduct.compareTo(credit) <= 0;
        }

        for(Trust trust: trusts){
            result = result.add(trust.getPriceProduct());
        }
        result = result.add(priceProduct);
        return result.compareTo(credit) <= 0;
        /*
        if (result.compareTo(credit) > 0) {
            return false;
        }
        return true;
        */
    }
}
