package com.finanzas_backend_spring.accounts_system.services;

import com.finanzas_backend_spring.accounts_system.models.Account;
import java.util.List;

public interface AccountService {
    List<Account> getAllAccounts ();
    List<Account> getAllAccountsByClient(Long clientId);
    Account getAccountById(Long id);
    Account save(Long clientId, Long maintenanceId,Long lineOfCreditId);
    void delete(Long id);
    Account changeMaintenance (Long id, Long maintenanceId);
}
