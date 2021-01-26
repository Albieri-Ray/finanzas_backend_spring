package com.finanzas_backend_spring.accounts_system.services.impl;

import com.finanzas_backend_spring.accounts_system.models.Account;
import com.finanzas_backend_spring.accounts_system.repositories.AccountRepository;
import com.finanzas_backend_spring.accounts_system.repositories.LineOfCreditRepository;
import com.finanzas_backend_spring.accounts_system.repositories.MaintenanceRepository;
import com.finanzas_backend_spring.accounts_system.services.AccountService;
import com.finanzas_backend_spring.user_system.repositories.ClientRepository;
import com.finanzas_backend_spring.user_system.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final LineOfCreditRepository lineOfCreditRepository;

    public AccountServiceImpl(AccountRepository accountRepository, ClientRepository clientRepository, MaintenanceRepository maintenanceRepository, LineOfCreditRepository lineOfCreditRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
        this.maintenanceRepository = maintenanceRepository;
        this.lineOfCreditRepository = lineOfCreditRepository;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> getAllAccountsByClient(Long clientId) {
        return accountRepository.findByClientId(clientId);
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(()->new NotFoundException("account","id",id));
    }

    @Override
    public Account save(Long clientId,Long maintenanceId,Long lineOfCreditId) {
        Account account = new Account();
        account.setClient(clientRepository.findById(clientId).orElseThrow(()->new NotFoundException("client","id", clientId)));
        account.setMaintenance(maintenanceRepository.findById(maintenanceId).orElseThrow(()->new NotFoundException("maintenance","id", maintenanceId)));
        account.setLineOfCredit(lineOfCreditRepository.findById(lineOfCreditId).orElseThrow(()->new NotFoundException("lineOfCredit","id", lineOfCreditId)));
        return accountRepository.save(account);
    }
    @Override
    public void delete(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(()->new NotFoundException("account","id",id));
        accountRepository.delete(account);
    }
}
